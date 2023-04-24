package com.iamincendium.source.query

public sealed interface Visibility {
    public object Public : Visibility
    public object Private : Visibility
    public data class Unknown internal constructor(val identifier: Int) : Visibility
}

public fun Visibility(byte: Byte): Visibility = when (val identifier = byte.toInt()) {
    0 -> Visibility.Public
    1 -> Visibility.Private
    else -> Visibility.Unknown(identifier)
}
