package com.iamincendium.source.query.message

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.iamincendium.source.query.error.ClientError
import com.iamincendium.source.query.error.SourceQueryError
import com.iamincendium.source.query.util.readIntLittleEndian
import com.iamincendium.source.query.util.runOrError

internal class MessageProcessor {
    fun <T : SourceResponseMessage> process(
        message: ByteArray,
        expectedType: MessageType.Response,
    ): Result<T, SourceQueryError> = runOrError {
        val header = when (val processedHeader = processHeader(message)) {
            is Ok -> processedHeader.value
            is Err -> return processedHeader
        }

        val type = MessageType(message[header.size])
        val payload = message.copyOfRange(header.size + type.size, message.size)

        if (type != expectedType) {
            return Err(ClientError.UnexpectedPacketResponse(type))
        }

        val responseMessage = when (type) {
            MessageType.Response.InfoResponse -> InfoResponseMessage(header, payload)
            MessageType.Response.PlayerResponse -> PlayerResponseMessage(header, payload)
            MessageType.Response.RulesResponse -> RulesResponseMessage(header, payload)

            else -> {
                return Err(ClientError.UnexpectedMessageResponse(type))
            }
        }

        @Suppress("UNCHECKED_CAST")
        responseMessage as T
    }

    private fun processHeader(message: ByteArray): Result<MessageHeader, SourceQueryError> = runOrError {
        when (val headerId = message.readIntLittleEndian(0).value) {
            HEADER_SINGLE -> SingleFragmentMessageHeader
            HEADER_FRAGMENT -> MultiFragmentSourceMessageHeader(message)
            else -> return Err(ClientError.UnexpectedPacketHeader(headerId))
        }
    }
}
