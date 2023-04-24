package com.iamincendium.source.query.message

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

    public sealed interface Request : MessageType {
        public val responseType: Response

        public object InfoRequest : Request {
            override val byte: Byte = A2S_INFO
            override val responseType: Response = Response.InfoResponse
        }
        public object PlayerRequest : Request {
            override val byte: Byte = A2S_PLAYER
            override val responseType: Response = Response.PlayerResponse
        }
        public object RulesRequest : Request {
            override val byte: Byte = A2S_RULES
            override val responseType: Response = Response.RulesResponse
        }
        public object ChallengeRequest : Request {
            override val byte: Byte = A2S_SERVERQUERY_GETCHALLENGE
            override val responseType: Response = Response.ChallengeResponse
        }
    }
    public sealed interface Response : MessageType {
        public object InfoResponse : Response {
            override val byte: Byte = S2A_INFO
        }
        public object PlayerResponse : Response {
            override val byte: Byte = S2A_PLAYER
        }
        public object RulesResponse : Response {
            override val byte: Byte = S2A_RULES
        }
        public object ChallengeResponse : Response {
            override val byte: Byte = S2C_CHALLENGE
        }
    }

    public data class UnknownMessage(override val byte: Byte) : MessageType
}

public fun MessageType(byte: Byte): MessageType = when (byte) {
    S2A_INFO -> MessageType.Request.InfoRequest
    A2S_INFO -> MessageType.Response.InfoResponse
    S2A_PLAYER -> MessageType.Request.PlayerRequest
    A2S_PLAYER -> MessageType.Response.PlayerResponse
    S2A_RULES -> MessageType.Request.RulesRequest
    A2S_RULES -> MessageType.Response.RulesResponse
    S2C_CHALLENGE -> MessageType.Request.ChallengeRequest
    A2S_SERVERQUERY_GETCHALLENGE -> MessageType.Response.ChallengeResponse
    else -> MessageType.UnknownMessage(byte)
}
