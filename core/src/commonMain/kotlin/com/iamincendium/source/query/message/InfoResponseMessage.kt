package com.iamincendium.source.query.message

import com.iamincendium.source.query.Environment
import com.iamincendium.source.query.ServerType
import com.iamincendium.source.query.Visibility
import com.iamincendium.source.query.VACStatus
import com.iamincendium.source.query.util.readAsciiCString
import com.iamincendium.source.query.util.readByte
import com.iamincendium.source.query.util.readIntLittleEndian
import com.iamincendium.source.query.util.readLongLittleEndian
import com.iamincendium.source.query.util.readShortLittleEndian
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
    val networkVersion: Byte
    val serverName: String
    val mapName: String
    val gameFolder: String
    val gameName: String
    val appId: Int
    val numberOfPlayers: Int
    val maxPlayers: Int
    val numberOfBots: Int
    val serverType: ServerType
    val environment: Environment
    val visibility: Visibility
    val vacStatus: VACStatus
    val gameVersion: String

    val gamePort: Int?
    val steamId: Long?

    val spectatorPort: Int?
    val spectatorServerName: String?

    val gameId: Long?

    init {
        val networkVersion = content.readByte(0)
        this.networkVersion = networkVersion.value

        val serverName = content.readAsciiCString(networkVersion.nextOffset)
        this.serverName = serverName.value

        val mapName = content.readAsciiCString(serverName.nextOffset)
        this.mapName = mapName.value

        val gameFolder = content.readAsciiCString(mapName.nextOffset)
        this.gameFolder = gameFolder.value

        val gameName = content.readAsciiCString(gameFolder.nextOffset)
        this.gameName = gameName.value

        val appId = content.readIntLittleEndian(gameName.nextOffset)
        this.appId = appId.value

        val numberOfPlayers = content.readIntLittleEndian(appId.nextOffset)
        this.numberOfPlayers = numberOfPlayers.value

        val maxPlayers = content.readIntLittleEndian(numberOfPlayers.nextOffset)
        this.maxPlayers = maxPlayers.value

        val numberOfBots = content.readIntLittleEndian(maxPlayers.nextOffset)
        this.numberOfBots = numberOfBots.value

        val serverType = content.readByte(numberOfBots.nextOffset)
        this.serverType = ServerType(serverType.value)

        val environment = content.readByte(serverType.nextOffset)
        this.environment = Environment(environment.value)

        val visibility = content.readByte(environment.nextOffset)
        this.visibility = Visibility(visibility.value)

        val vacStatus = content.readByte(visibility.nextOffset)
        this.vacStatus = VACStatus(vacStatus.value)

        val gameVersion = content.readAsciiCString(vacStatus.nextOffset)
        this.gameVersion = gameVersion.value

        val extraDataFlag = content.readByte(gameVersion.nextOffset)
        var nextOffset = extraDataFlag.nextOffset

        if (extraDataFlag.value and EDF_GAME_PORT == EDF_GAME_PORT) {
            val gamePort = content.readShortLittleEndian(nextOffset)
            this.gamePort = gamePort.value.toInt()

            nextOffset = gamePort.nextOffset
        } else {
            gamePort = null
        }

        if (extraDataFlag.value and EDF_SERVER_ID == EDF_SERVER_ID) {
            val steamId = content.readLongLittleEndian(nextOffset)
            this.steamId = steamId.value

            nextOffset = steamId.nextOffset
        } else {
            steamId = null
        }

        if (extraDataFlag.value and EDF_SPECTATOR_INFO == EDF_SPECTATOR_INFO) {
            val spectatorPort = content.readShortLittleEndian(nextOffset)
            this.spectatorPort = spectatorPort.value.toInt()

            val spectatorServerName = content.readAsciiCString(spectatorPort.nextOffset)
            this.spectatorServerName = spectatorServerName.value

            nextOffset = spectatorServerName.nextOffset
        } else {
            spectatorPort = null
            spectatorServerName = null
        }

        if (extraDataFlag.value and EDF_GAME_ID == EDF_GAME_ID) {
            val gameId = content.readLongLittleEndian(nextOffset)
            this.gameId = gameId.value

            nextOffset = gameId.nextOffset
        } else {
            gameId = null
        }
    }
}
