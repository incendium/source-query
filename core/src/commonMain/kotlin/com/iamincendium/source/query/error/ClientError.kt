package com.iamincendium.source.query.error

import com.iamincendium.source.query.message.MessageType
import com.iamincendium.source.query.util.toByteArrayLittleEndian
import com.iamincendium.source.query.util.toHexDump
import com.iamincendium.source.query.util.toHexString

public sealed interface ClientError : SourceQueryError {
    public object InvalidChallengeError : ClientError {
        override val message: String =
            "Received a challenge response after sending a request with a proper challenge number."
    }
    public data class UnexpectedPacketHeader(val headerId: Int) : ClientError {
        override val message: String = "Got unexpected packet header: ${headerId.toHexString()}"
    }
    public data class UnexpectedPacketResponse(val messageType: MessageType) : ClientError {
        override val message: String = "Got unexpected packet response: ${messageType::class.simpleName}"
    }
    public data class UnexpectedMessageResponse(val messageType: MessageType) : ClientError {
        override val message: String = "Got unexpected message response: ${messageType::class.simpleName}"
    }
}
