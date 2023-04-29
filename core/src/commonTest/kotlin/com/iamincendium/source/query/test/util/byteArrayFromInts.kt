package com.iamincendium.source.query.test.util

/**
 * Create a [ByteArray] instance from [Int] values. This is handy to create readable [ByteArray] declarations without
 * having to explicitly cast each hex format number into a [Byte].
 *
 * The following:
 *
 *     byteArrayOf(0x00, 0xFF.toByte())
 *
 * Is instead:
 *
 *     byteArrayFromInts(0x00, 0xFF)
 *
 */
fun byteArrayFromInts(vararg values: Int) = values.map { it.toByte() }.toByteArray()
