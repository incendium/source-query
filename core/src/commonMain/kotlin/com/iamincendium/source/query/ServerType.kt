package com.iamincendium.source.query

public sealed interface ServerType {
    public object Dedicated : ServerType
    public object Local : ServerType
    public object TVProxy : ServerType
    public data class Unknown internal constructor(val identifier: Char) : ServerType
}

internal fun ServerType(byte: Byte) = when (val identifier = byte.toInt().toChar()) {
    'd' -> ServerType.Dedicated
    'l' -> ServerType.Local
    'p' -> ServerType.TVProxy
    else -> ServerType.Unknown(identifier)
}
