package com.iamincendium.source.query.message

/**
 * `A2S_RULES`
 *
 * Packet used to request the rules list from a Source server.
 *
 * | Data      | C Type   | Kotlin Type  | Value                        |
 * | --------- | -------- | ------------ | ---------------------------- |
 * | Header    | `byte`   | [Byte]       | 'V' (0x56)                   |
 * | Challenge | `long`   | [Int]        | 4-byte challenge (sometimes) |
 *
 * @see ChallengeResponseMessage
 * @see PlayerResponseMessage
 */
internal class RulesRequestMessage(challenge: Int) :
    SourceRequestMessage(MessageType.Request.RulesRequest, buildChallengePayload(challenge))
