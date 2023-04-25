package com.iamincendium.source.query.util

import okio.Buffer

private const val ASCII_NULL = 0x00.toByte()

/**
 * Read a C-style string (NULL terminated)
 */
internal fun Buffer.readNullTerminatedUtf8String(): String {
    val peekSource = peek()
    var byteCount = 0L
    var byte = peekSource.readByte()
    while (byte != ASCII_NULL) {
        byteCount++
        byte = peekSource.readByte()
    }

    val value = readUtf8(byteCount)
    skip(1)
    return value
}

/**
 * Read a [Float] ordered in little endian order.
 */
public fun Buffer.readFloatLe(): Float = Float.fromBits(readIntLe())

/**
 * Read a [Float] ordered in big endian order.
 */
public fun Buffer.readFloat(): Float = Float.fromBits(readInt())

/**
 * Read a [Float] ordered in little endian order.
 */
public fun Buffer.readDoubleLe(): Double = Double.fromBits(readLongLe())

/**
 * Read a [Float] ordered in big endian order.
 */
public fun Buffer.readDouble(): Double = Double.fromBits(readLong())
