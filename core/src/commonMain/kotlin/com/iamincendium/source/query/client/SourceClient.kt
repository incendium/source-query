package com.iamincendium.source.query.client

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.iamincendium.source.query.error.ClientError
import com.iamincendium.source.query.error.GenericError
import com.iamincendium.source.query.error.SourceQueryError
import com.iamincendium.source.query.message.ChallengeResponseMessage
import com.iamincendium.source.query.message.DEFAULT_SOURCE_CHALLENGE
import com.iamincendium.source.query.message.InfoRequestMessage
import com.iamincendium.source.query.message.MessageProcessor
import com.iamincendium.source.query.message.MessageType
import com.iamincendium.source.query.message.PlayerRequestMessage
import com.iamincendium.source.query.message.RulesRequestMessage
import com.iamincendium.source.query.message.SourceResponseMessage
import com.iamincendium.source.query.util.runOrError
import kotlinx.coroutines.withTimeout

internal class SourceClient(
    private val address: String,
    private val port: Int,
    private val clientTransport: ClientTransport = ClientTransport(address, port),
    private val messageProcessor: MessageProcessor = MessageProcessor(),
    private val fetchTimeout: Long = DEFAULT_TIMEOUT,
) {
    suspend fun <T : SourceResponseMessage> fetch(
        type: MessageType.Request,
        challengeNumber: Int? = null,
    ): Result<T, SourceQueryError> = runOrError {
        val requestChallenge = challengeNumber ?: DEFAULT_SOURCE_CHALLENGE
        val requestMessage = when (type) {
            MessageType.Request.InfoRequest -> InfoRequestMessage
            MessageType.Request.PlayerRequest -> PlayerRequestMessage(requestChallenge)
            MessageType.Request.RulesRequest -> RulesRequestMessage(requestChallenge)

            else -> return Err(GenericError("Message type is not supported."))
        }

        val requestResult = clientTransport.send(requestMessage.bytes)
        if (requestResult is Err) {
            return requestResult
        }

        val responseResult = withTimeout(fetchTimeout) {
            clientTransport.receive()
        }

        val payload = when (responseResult) {
            is Ok -> responseResult.value
            is Err -> return responseResult
        }

        val responseMessage = when (val result = messageProcessor.process<T>(payload, type.responseType)) {
            is Ok -> result.value
            is Err -> return result
        }

        if (responseMessage is ChallengeResponseMessage) {
            if (challengeNumber != null) {
                return Err(ClientError.InvalidChallengeError)
            }

            return fetch(type, responseMessage.challengeNumber)
        }

        responseMessage
    }
}
