package com.iamincendium.source.query

public data class ServerInfo(
    val networkVersion: Byte,
    val serverName: String,
    val mapName: String,
    val gameFolder: String,
    val gameName: String,
    val appId: Int,
    val numberOfPlayers: Int,
    val maxPlayers: Int,
    val numberOfBots: Int,
    val serverType: ServerType,
    val environment: Environment,
    val visibility: Visibility,
    val vacStatus: VACStatus,
    val gameVersion: String,
    val extraInfo: List<ExtraInfo>,
) {
    val gamePort: Int?
    val steamId: Long?
    val spectatorPort: Int?
    val spectatorServerName: String?
    val gameId: Long?

    init {
        var gamePort: Int? = null
        var steamId: Long? = null
        var spectatorPort: Int? = null
        var spectatorServerName: String? = null
        var gameId: Long? = null

        for (info in extraInfo) {
            when (info) {
                is ExtraInfo.GamePort -> gamePort = info.port
                is ExtraInfo.SteamID -> steamId = info.steamId
                is ExtraInfo.SourceTV -> {
                    spectatorPort = info.port
                    spectatorServerName = info.name
                }
                is ExtraInfo.Keywords -> { /* Not currently supported */ }
                is ExtraInfo.GameID -> gameId = info.gameId
            }
        }

        this.gamePort = gamePort
        this.steamId = steamId
        this.spectatorPort = spectatorPort
        this.spectatorServerName = spectatorServerName
        this.gameId = gameId
    }

    public sealed interface ExtraInfo {
        public data class GamePort(val port: Int) : ExtraInfo
        public data class SteamID(val steamId: Long) : ExtraInfo
        public data class SourceTV(val port: Int, val name: String) : ExtraInfo
        public data class Keywords(val keywords: String) : ExtraInfo
        public data class GameID(val gameId: Long) : ExtraInfo
    }
}
