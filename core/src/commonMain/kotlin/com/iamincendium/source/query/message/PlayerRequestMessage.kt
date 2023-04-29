package com.iamincendium.source.query.message

/**
 * `A2S_PLAYER`
 *
 * Packet used to request the player list from a Source server.
 *
 * | Data      | C Type   | Kotlin Type  | Value                        |
 * | --------- | -------- | ------------ | ---------------------------- |
 * | Header    | `byte`   | [Byte]       | 'U' (0x55)                   |
 * | Challenge | `long`   | [Int]        | 4-byte challenge (sometimes) |
 *
 * @see ChallengeResponseMessage
 * @see PlayerResponseMessage
 */
public class PlayerRequestMessage(challenge: Int) :
    SourceRequestMessage(MessageType.Request.PlayerRequest, buildChallengePayload(challenge))
