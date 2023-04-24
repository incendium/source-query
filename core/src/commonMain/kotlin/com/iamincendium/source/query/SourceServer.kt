package com.iamincendium.source.query

import com.iamincendium.source.query.client.SourceClient
import com.iamincendium.source.query.message.MessageProcessor

public class SourceServer(address: String, port: Int) {
    private val client = SourceClient(address, port)
    private val parser = MessageProcessor()

    public suspend fun fetchInfo(): ServerInfo {
        TODO("Fetch server info packet from client and parse it")
    }
    public suspend fun fetchPlayers(): List<Player> {
        TODO("Fetch player info packet from client and parse it")
    }
    public suspend fun fetchRules(): Map<String, String> {
        TODO("Fetch rules info packet from client and parse it")
    }
}
