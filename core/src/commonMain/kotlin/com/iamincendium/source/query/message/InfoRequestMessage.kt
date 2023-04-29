package com.iamincendium.source.query.message

import okio.Buffer

internal const val INFO_REQUEST_CONTENT = "Source Engine Query\u0000"

/**
 * `A2S_INFO`
 *
 * Packet used to request information from a Source server.
 *
 * | Data      | C Type   | Kotlin Type  | Value                        |
 * | --------- | -------- | ------------ | ---------------------------- |
 * | Header    | `byte`   | [Byte]       | 'T' (0x54)                   |
 * | Payload   | `string` | [String]     | "Source Engine Query\u0000"  |
 * | Challenge | `long`   | [Int]        | 4-byte challenge (sometimes) |
 *
 * @see ChallengeResponseMessage
 * @see InfoResponseMessage
 */
internal class InfoRequestMessage(
    challenge: Int? = null,
) : SourceRequestMessage(MessageType.Request.InfoRequest, buildPayload(challenge)) {
    private companion object {
        private fun buildPayload(challenge: Int?): ByteArray {
            val buffer = Buffer()
            buffer.writeUtf8(INFO_REQUEST_CONTENT)
            if (challenge != null) {
                buffer.writeIntLe(challenge)
            }
            return buffer.readByteArray()
        }
    }
}
