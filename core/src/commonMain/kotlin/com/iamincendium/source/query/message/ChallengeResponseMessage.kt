package com.iamincendium.source.query.message

import com.iamincendium.source.query.util.readIntLittleEndian

/**
 * `S2A_CHALLENGE`
 *
 * Packet received from a Source server with challenge information. This is typically received in conjunction with
 * [PlayerRequestMessage] or [RulesRequestMessage] after sending a request with an empty (`-1`) challenge.
 *
 * | Data      | C Type   | Kotlin Type  | Value                        |
 * | --------- | -------- | ------------ | ---------------------------- |
 * | Header    | `byte`   | [Byte]       | 'A' (0x41)                   |
 * | Challenge | `long`   | [Int]        | The challenge number to use. |
 *
 * @see PlayerRequestMessage
 * @see RulesRequestMessage
 */
internal class ChallengeResponseMessage(
    header: MessageHeader,
    content: ByteArray,
) : SourceResponseMessage(MessageType.Response.ChallengeResponse, header, content) {
    val challengeNumber: Int = content.readIntLittleEndian(header.size + 0).value
}
