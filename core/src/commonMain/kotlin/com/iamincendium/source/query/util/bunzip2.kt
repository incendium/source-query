package com.iamincendium.source.query.util

/**
 * Decompress a [ByteArray] that was compressed using bzip2.
 */
internal expect fun bunzip2(content: ByteArray): ByteArray
