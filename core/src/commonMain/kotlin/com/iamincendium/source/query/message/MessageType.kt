package com.iamincendium.source.query.message

import com.iamincendium.source.query.Player
import com.iamincendium.source.query.ServerInfo

private const val S2A_INFO: Byte = 0x49
private const val A2S_INFO: Byte = 0x54
private const val S2A_PLAYER: Byte = 0x44
private const val A2S_PLAYER: Byte = 0x55
private const val S2A_RULES: Byte = 0x45
private const val A2S_RULES: Byte = 0x56
private const val S2C_CHALLENGE: Byte = 0x41
private const val A2S_SERVERQUERY_GETCHALLENGE: Byte = 0x57

public sealed interface MessageType {
    public val byte: Byte
    public val size: Int get() = Byte.SIZE_BYTES

    public sealed interface Request<T : Any> : MessageType {
        public val responseType: Response<T>

        public object InfoRequest : Request<ServerInfo> {
            override val byte: Byte = A2S_INFO
            override val responseType: Response<ServerInfo> = Response.InfoResponse
        }
        public object PlayerRequest : Request<List<Player>> {
            override val byte: Byte = A2S_PLAYER
            override val responseType: Response<List<Player>> = Response.PlayerResponse
        }
        public object RulesRequest : Request<Map<String, Any>> {
            override val byte: Byte = A2S_RULES
            override val responseType: Response<Map<String, Any>> = Response.RulesResponse
        }
    }
    public sealed interface Response<T : Any> : MessageType {
        public object InfoResponse : Response<ServerInfo> {
            override val byte: Byte = S2A_INFO
        }
        public object PlayerResponse : Response<List<Player>> {
            override val byte: Byte = S2A_PLAYER
        }
        public object RulesResponse : Response<Map<String, Any>> {
            override val byte: Byte = S2A_RULES
        }
        public object ChallengeResponse : Response<Unit> {
            override val byte: Byte = S2C_CHALLENGE
        }
    }

    public data class UnknownMessage(override val byte: Byte) : MessageType
}

public fun MessageType(byte: Byte): MessageType = when (byte) {
    A2S_INFO -> MessageType.Request.InfoRequest
    S2A_INFO -> MessageType.Response.InfoResponse
    A2S_PLAYER -> MessageType.Request.PlayerRequest
    S2A_PLAYER -> MessageType.Response.PlayerResponse
    A2S_RULES -> MessageType.Request.RulesRequest
    S2A_RULES -> MessageType.Response.RulesResponse
    S2C_CHALLENGE -> MessageType.Response.ChallengeResponse
    else -> MessageType.UnknownMessage(byte)
}
