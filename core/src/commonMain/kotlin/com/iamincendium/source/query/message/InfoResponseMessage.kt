package com.iamincendium.source.query.message

import com.iamincendium.source.query.Environment
import com.iamincendium.source.query.ServerType
import com.iamincendium.source.query.VACStatus
import com.iamincendium.source.query.Visibility
import com.iamincendium.source.query.util.readNullTerminatedUtf8String
import okio.Buffer
import kotlin.experimental.and

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
    private val buffer = Buffer().also { it.write(content) }

    val networkVersion: Byte = buffer.readByte()
    val serverName: String = buffer.readNullTerminatedUtf8String()
    val mapName: String = buffer.readNullTerminatedUtf8String()
    val gameFolder: String = buffer.readNullTerminatedUtf8String()
    val gameName: String = buffer.readNullTerminatedUtf8String()
    val appId: Int = buffer.readShortLe().toInt()
    val numberOfPlayers: Int = buffer.readByte().toInt()
    val maxPlayers: Int = buffer.readByte().toInt()
    val numberOfBots: Int = buffer.readByte().toInt()
    val serverType: ServerType = ServerType(buffer.readByte())
    val environment: Environment = Environment(buffer.readByte())
    val visibility: Visibility = Visibility(buffer.readByte())
    val vacStatus: VACStatus = VACStatus(buffer.readByte())
    val gameVersion: String = buffer.readNullTerminatedUtf8String()

    val extraDataFlag = if (!buffer.exhausted()) {
        buffer.readByte()
    } else {
        0x00
    }

    val gamePort: Int? = if (extraDataFlag and EDF_GAME_PORT == EDF_GAME_PORT) {
        buffer.readShortLe().toInt()
    } else {
        null
    }

    val steamId: Long? = if (extraDataFlag and EDF_SERVER_ID == EDF_SERVER_ID) {
        buffer.readLongLe()
    } else {
        null
    }

    val spectatorPort: Int?
    val spectatorServerName: String?
    init {
        if (extraDataFlag and EDF_SPECTATOR_INFO == EDF_SPECTATOR_INFO) {
            spectatorPort = buffer.readShortLe().toInt()
            spectatorServerName = buffer.readNullTerminatedUtf8String()
        } else {
            spectatorPort = null
            spectatorServerName = null
        }
    }

    val gameId: Long? = if (extraDataFlag and EDF_GAME_ID == EDF_GAME_ID) {
        buffer.readLongLe()
    } else {
        null
    }
}
