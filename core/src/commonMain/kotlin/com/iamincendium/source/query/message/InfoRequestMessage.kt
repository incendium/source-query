package com.iamincendium.source.query.message

internal val INFO_REQUEST_CONTENT = "Source Engine Query\u0000".encodeToByteArray()

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
internal object InfoRequestMessage : SourceRequestMessage(MessageType.Request.InfoRequest, INFO_REQUEST_CONTENT)
