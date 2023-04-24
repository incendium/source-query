package com.iamincendium.source.query.util

/**
 * Calculate the CRC32 checksum of the provided [ByteArray].
 */
internal expect fun crc32(payload: ByteArray): Long
