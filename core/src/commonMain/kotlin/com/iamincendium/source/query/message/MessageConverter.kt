package com.iamincendium.source.query.message

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.iamincendium.source.query.Player
import com.iamincendium.source.query.ServerInfo
import com.iamincendium.source.query.error.ConvertError
import com.iamincendium.source.query.error.SourceQueryError

internal class MessageConverter {
    fun <T : Any> convert(message: SourceResponseMessage): Result<T, SourceQueryError> = when (message) {
        is InfoResponseMessage -> convertInfoResponse(message)
        is PlayerResponseMessage -> convertPlayerResponse(message)
        is RulesResponseMessage -> convertRulesResponse(message)

        else -> Err(ConvertError.UnsupportedMessageType(message::class))
    }.map {
        @Suppress("UNCHECKED_CAST")
        it as T
    }

    private fun convertInfoResponse(
        message: InfoResponseMessage,
    ): Result<ServerInfo, SourceQueryError> {
        val extraInfo = buildList {
            if (message.gamePort != null) {
                add(ServerInfo.ExtraInfo.GamePort(message.gamePort))
            }

            if (message.steamId != null) {
                add(ServerInfo.ExtraInfo.SteamID(message.steamId))
            }

            if (message.spectatorPort != null && message.spectatorServerName != null) {
                add(ServerInfo.ExtraInfo.SourceTV(message.spectatorPort, message.spectatorServerName))
            }

            if (message.gameId != null) {
                add(ServerInfo.ExtraInfo.GameID(message.gameId))
            }
        }

        val serverInfo = ServerInfo(
            message.networkVersion,
            message.serverName,
            message.mapName,
            message.gameFolder,
            message.gameName,
            message.appId,
            message.numberOfPlayers,
            message.maxPlayers,
            message.numberOfBots,
            message.serverType,
            message.environment,
            message.visibility,
            message.vacStatus,
            message.gameVersion,
            extraInfo,
        )

        return Ok(serverInfo)
    }

    private fun convertPlayerResponse(
        message: PlayerResponseMessage,
    ): Result<List<Player>, SourceQueryError> {
        return Ok(message.players)
    }

    private fun convertRulesResponse(
        message: RulesResponseMessage,
    ): Result<Map<String, String>, SourceQueryError> {
        return Ok(message.rules)
    }
}
