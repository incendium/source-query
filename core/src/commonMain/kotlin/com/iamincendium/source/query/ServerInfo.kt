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
    val gamePort: Int?,
    val steamId: Long?,
    val spectatorPort: Int?,
    val spectatorServerName: String?,
    val gameId: Long?,
)
