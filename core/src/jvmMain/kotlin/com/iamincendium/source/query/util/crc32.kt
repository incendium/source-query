package com.iamincendium.source.query.util

import java.util.zip.CRC32

internal actual fun crc32(payload: ByteArray): Long {
    val crc32 = CRC32()
    crc32.update(payload)
    return crc32.value
}
