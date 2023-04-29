package com.iamincendium.source.query.client

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onSuccess
import com.iamincendium.source.query.error.ClientError
import com.iamincendium.source.query.error.SourceQueryError
import com.iamincendium.source.query.message.ChallengeResponseMessage
import com.iamincendium.source.query.message.DEFAULT_SOURCE_CHALLENGE
import com.iamincendium.source.query.message.InfoRequestMessage
import com.iamincendium.source.query.message.MessageConverter
import com.iamincendium.source.query.message.MessageProcessor
import com.iamincendium.source.query.message.MessageType
import com.iamincendium.source.query.message.PlayerRequestMessage
import com.iamincendium.source.query.message.RulesRequestMessage
import com.iamincendium.source.query.util.flatten
import com.iamincendium.source.query.util.runOrError
import kotlinx.coroutines.withTimeout
import okio.Closeable

internal class SourceClient(
    private val address: String,
    private val port: Int,
    private val clientTransport: ClientTransport = ClientTransport(address, port),
    private val processor: MessageProcessor = MessageProcessor(),
    private val converter: MessageConverter = MessageConverter(),
    private val fetchTimeout: Long = DEFAULT_TIMEOUT,
) : Closeable {
    suspend fun <T : Any> fetch(
        messageType: MessageType.Request<T>,
        challengeNumber: Int? = null,
    ): Result<T, SourceQueryError> = runOrError {
        val requestMessage = when (messageType) {
            MessageType.Request.InfoRequest -> InfoRequestMessage(challengeNumber)
            MessageType.Request.PlayerRequest -> PlayerRequestMessage(challengeNumber ?: DEFAULT_SOURCE_CHALLENGE)
            MessageType.Request.RulesRequest -> RulesRequestMessage(challengeNumber ?: DEFAULT_SOURCE_CHALLENGE)
        }

        clientTransport.send(requestMessage.bytes)
            .andThen { withTimeout(fetchTimeout) { clientTransport.receive() } }
            .andThen { processor.process(it, messageType.responseType) }
            .onSuccess {
                if (it is ChallengeResponseMessage) {
                    return if (challengeNumber == null) {
                        fetch(messageType, it.challengeNumber)
                    } else {
                        Err(ClientError.InvalidChallengeError)
                    }
                }
            }
            .andThen { converter.convert<T>(it) }
    }.flatten()

    override fun close() {
        clientTransport.close()
    }
}
