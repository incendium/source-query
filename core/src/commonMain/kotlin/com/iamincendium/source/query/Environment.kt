package com.iamincendium.source.query

public sealed interface Environment {
    public object Linux : Environment
    public object Windows : Environment
    public object MacOS : Environment
    public data class Unknown internal constructor(val identifier: Char) : Environment
}

public fun Environment(byte: Byte): Environment = when (val identifier = byte.toInt().toChar()) {
    'l' -> Environment.Linux
    'w' -> Environment.Windows
    'm', 'o' -> Environment.MacOS
    else -> Environment.Unknown(identifier)
}
