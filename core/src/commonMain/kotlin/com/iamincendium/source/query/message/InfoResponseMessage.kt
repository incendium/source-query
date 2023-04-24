package com.iamincendium.source.query.message

import com.iamincendium.source.query.Environment
import com.iamincendium.source.query.ServerType
import com.iamincendium.source.query.Visibility
import com.iamincendium.source.query.VACStatus

/**
 * `S2A_INFO`
 *
 * Packet received from a Source server with general server information.
 *
 * | Data                  | C Type   | Kotlin Type   | Value                                                                     |
 * | --------------------- | -------- | ------------- | ------------------------------------------------------------------------- |
 * | Header                | `byte`   | [Byte]        | 'I' (0x49)                                                                |
 * | Protocol              | `byte`   | [Byte]        | Protocol version used by the server.                                      |
 * | Name                  | `string` | [String]      | Name of the server.                                                       |
 * | Map                   | `string` | [String]      | Map the server has currently loaded.                                      |
 * | Folder                | `string` | [String]      | Name of the folder containing the game files.                             |
 * | Game                  | `string` | [String]      | Full name of the game.                                                    |
 * | ID                    | `short`  | [Short]       | Steam Application ID of game.                                             |
 * | Players               | `byte`   | [Byte]        | Number of players on the server.                                          |
 * | Max. Players          | `byte`   | [Byte]        | Maximum number of players the server reports it can hold.                 |
 * | Bots                  | `byte`   | [Byte]        | Number of bots on the server.                                             |
 * | Environment           | `byte`   | [Environment] | Indicates the operating system of the server.                             |
 * | Server type           | `byte`   | [ServerType]  | Indicates the type of server.                                             |
 * | Visibility            | `byte`   | [Visibility]  | Indicates whether the server requires a password.                         |
 * | VAC                   | `byte`   | [VACStatus]   | Specifies whether the server uses VAC.                                    |
 * | Version               | `string` | [String]      | Version of the game installed on the server.                              |
 * | Extra Data Flag (EDF) | `byte`   | [Byte]        | If present, this specifies which additional data fields will be included. |
 *
 * Additional Data Fields:
 *
 * | EDF Criteria | Data     | C Type      | Kotlin Type | Value                                                                  |
 * | ------------ | -------- | ----------- | ----------- | ---------------------------------------------------------------------- |
 * | `EDF & 0x80` | Port     | `short`     | [Short]     | The server's game port number.                                         |
 * | `EDF & 0x10` | SteamID  | `long long` | [Long]      | Server's SteamID.                                                      |
 * | `EDF & 0x40` | Port     | `short`     | [Short]     | Spectator port number for SourceTV.                                    |
 * | `EDF & 0x40` | Name     | `string`    | [String]    | Name of the spectator server for SourceTV.                             |
 * | `EDF & 0x20` | Keywords | `string`    | [String]    | Tags that describe the game according to the server (for future use.)  |
 * | `EDF & 0x01` | GameID   | `long long` | [Long]      | The server's 64-bit GameID. If this is present, a more accurate AppID is present in the low 24 bits. The earlier AppID could have been truncated as it was forced into 16-bit storage. |
 *
 * @see InfoRequestMessage
 */
@Suppress("MaxLineLength")
internal class InfoResponseMessage(
    header: MessageHeader,
    content: ByteArray,
) : SourceResponseMessage(MessageType.Response.PlayerResponse, header, content) {
    val networkVersion: Byte = TODO()
    val serverName: String = TODO()
    val mapName: String = TODO()
    val gameFolder: String = TODO()
    val gameName: String = TODO()
    val appId: Int = TODO()
    val numberOfPlayers: Int = TODO()
    val maxPlayers: Int = TODO()
    val numberOfBots: Int = TODO()
    val serverType: ServerType = TODO()
    val environment: Environment = TODO()
    val visibility: Visibility = TODO()
    val vacStatus: VACStatus = TODO()
    val gameVersion: String = TODO()

    private val extraDataFlag: Byte = TODO()

    val gamePort: Int? = TODO()
    val steamId: Long? = TODO()

    val spectatorPort: Int? = TODO()
    val spectatorServerName: String? = TODO()

    val gameId: Long? = TODO()
}
