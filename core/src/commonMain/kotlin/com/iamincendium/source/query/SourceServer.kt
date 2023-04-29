package com.iamincendium.source.query

import com.github.michaelbull.result.Result
import com.iamincendium.source.query.client.SourceClient
import com.iamincendium.source.query.error.SourceQueryError
import com.iamincendium.source.query.message.MessageType
import okio.Closeable

public class SourceServer(address: String, port: Int) : Closeable {
    private val client = SourceClient(address, port)

    public suspend fun fetchInfo(): Result<ServerInfo, SourceQueryError> =
        client.fetch(MessageType.Request.InfoRequest)

    public suspend fun fetchPlayers(): Result<List<Player>, SourceQueryError> =
        client.fetch(MessageType.Request.PlayerRequest)

    public suspend fun fetchRules(): Result<Map<String, Any>, SourceQueryError> =
        client.fetch(MessageType.Request.RulesRequest)

    override fun close() {
        client.close()
    }
}
