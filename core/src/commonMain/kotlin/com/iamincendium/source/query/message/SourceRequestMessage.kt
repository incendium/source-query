package com.iamincendium.source.query.message

import okio.Buffer

/**
 * Base message for constructing outgoing messages towards a server which supports the Source query protocol.
 *
 * @param type the message type of the outgoing message
 * @param payload the content of the outgoing message; Note: the packet will be pre-constructed with the header and
 * type, so only the actual payload (if any) should be provided here.
 */
public sealed class SourceRequestMessage(private val type: MessageType, payload: ByteArray) : SourceMessage {
    protected val payload: ByteArray = payload.copyOf()

    public override val bytes: ByteArray get() = SingleFragmentMessageHeader.bytes + type.byte + payload

    internal companion object {
        fun buildChallengePayload(challenge: Int): ByteArray =
            Buffer().also { it.writeIntLe(challenge) }.readByteArray()
    }
}
