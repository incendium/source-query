package com.iamincendium.source.query

public sealed interface VACStatus {
    public object Disabled : VACStatus
    public object Enabled : VACStatus
    public data class Unknown internal constructor(val identifier: Int) : VACStatus
}

public fun VACStatus(byte: Byte): VACStatus = when (val identifier = byte.toInt()) {
    0 -> VACStatus.Disabled
    1 -> VACStatus.Enabled
    else -> VACStatus.Unknown(identifier)
}
