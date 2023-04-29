package com.iamincendium.source.query.message

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.iamincendium.source.query.error.ClientError
import com.iamincendium.source.query.error.SourceQueryError
import com.iamincendium.source.query.util.runOrError
import okio.Buffer

internal class MessageProcessor {
    fun process(
        message: ByteArray,
        expectedType: MessageType.Response<*>,
    ): Result<SourceResponseMessage, SourceQueryError> = runOrError {
        val header = when (val processedHeader = processHeader(message)) {
            is Ok -> processedHeader.value
            is Err -> return processedHeader
        }

        val type = MessageType(message[header.size])
        val payload = message.copyOfRange(header.size + type.size, message.size)

        if (type != expectedType) {
            return Err(ClientError.UnexpectedPacketResponse(type))
        }

        when (type) {
            MessageType.Response.InfoResponse -> InfoResponseMessage(header, payload)
            MessageType.Response.PlayerResponse -> PlayerResponseMessage(header, payload)
            MessageType.Response.RulesResponse -> RulesResponseMessage(header, payload)

            else -> {
                return Err(ClientError.UnexpectedMessageResponse(type))
            }
        }
    }

    private fun processHeader(message: ByteArray): Result<MessageHeader, SourceQueryError> = runOrError {
        val buffer = Buffer()
        buffer.write(message)

        when (val headerId = buffer.readIntLe()) {
            HEADER_SINGLE -> SingleFragmentMessageHeader
            HEADER_FRAGMENT -> MultiFragmentSourceMessageHeader(buffer)
            else -> return Err(ClientError.UnexpectedPacketHeader(headerId))
        }
    }
}
