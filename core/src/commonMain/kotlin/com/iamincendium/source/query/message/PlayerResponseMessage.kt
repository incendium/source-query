package com.iamincendium.source.query.message

import com.iamincendium.source.query.Player
import com.iamincendium.source.query.util.readFloatLe
import com.iamincendium.source.query.util.readNullTerminatedUtf8String
import okio.Buffer

/**
 * `S2A_PLAYER`
 *
 * Packet received from a Source server with the player list.
 *
 * | Data                  | C Type   | Kotlin Type   | Value                                             |
 * | --------------------- | -------- | ------------- | ------------------------------------------------- |
 * | Header                | `byte`   | [Byte]        | 'D' (0x44)                                        |
 * | Players               | `byte`   | [Byte]        | Number of players whose information was gathered. |
 *
 * Each player will have the following entry in the response:
 *
 * | Data     | C Type      | Kotlin Type | Value                                                      |
 * | -------- | ----------- | ----------- | ---------------------------------------------------------- |
 * | Index    | `byte`      | [Byte]      | Index of player chunk starting from 0.                     |
 * | Name     | `string`    | [String]    | Name of the player.                                        |
 * | Score    | `long`      | [Int]       | Player's score (usually "frags" or "kills".)               |
 * | Duration | `float`     | [Float]     | Time (in seconds) player has been connected to the server. |
 *
 * @see PlayerRequestMessage
 */
internal class PlayerResponseMessage(
    header: MessageHeader,
    content: ByteArray,
) : SourceResponseMessage(MessageType.Response.PlayerResponse, header, content) {
    private val buffer = Buffer().also { it.write(content) }

    val players: List<Player> = buildList {
        repeat(buffer.readByte().toInt()) {
            this += Player(
                buffer.readByte().toInt(),
                buffer.readNullTerminatedUtf8String(),
                buffer.readIntLe(),
                buffer.readFloatLe(),
            )
        }
    }
}
