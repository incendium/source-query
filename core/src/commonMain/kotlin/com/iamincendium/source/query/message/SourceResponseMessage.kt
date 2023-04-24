package com.iamincendium.source.query.message

internal sealed class SourceResponseMessage(
    private val type: MessageType,
    protected val header: MessageHeader,
    content: ByteArray,
) : SourceMessage {
    protected val payload: ByteArray = content.copyOf()

    override val bytes: ByteArray get() = header.bytes + type.byte + payload
}
