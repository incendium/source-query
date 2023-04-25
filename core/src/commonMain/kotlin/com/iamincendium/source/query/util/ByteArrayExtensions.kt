@file:Suppress("MagicNumber", "TooManyFunctions")
package com.iamincendium.source.query.util

private const val NUL: Byte = 0x00

/**
 * Throw an [IndexOutOfBoundsException] if the desired bytes are out of bounds.
 */
private inline fun ByteArray.assertIndexIsAvailable(
    offset: Int,
    length: Int,
    message: () -> String,
) {
    if (offset + length > size) {
        throw IndexOutOfBoundsException(message())
    }
}

/**
 * Throw an [IndexOutOfBoundsException] if the desired bytes are out of bounds.
 */
internal fun ByteArray.assertCanRead(
    offset: Int,
    bytesToRead: Int,
) = assertIndexIsAvailable(offset, bytesToRead) {
    "Attempted to read $bytesToRead bytes at offset $offset from an array of size $size."
}

/**
 * Throw an [IndexOutOfBoundsException] if the desired bytes are out of bounds.
 */
internal fun ByteArray.assertCanWrite(
    offset: Int,
    bytesToWrite: Int,
) = assertIndexIsAvailable(offset, bytesToWrite) {
    "Attempted to write $bytesToWrite bytes at offset $offset from an array of size $size."
}

/**
 * Holder
 */
public data class ReadValue<T : Any>(val value: T, val readBytes: Int, val nextOffset: Int)

/**
 * Read a [Byte] from a specific offset.
 */
public fun ByteArray.readByte(offset: Int): ReadValue<Byte> {
    assertCanRead(offset, Byte.SIZE_BYTES)

    return ReadValue(get(offset), Byte.SIZE_BYTES, offset + Byte.SIZE_BYTES)
}

/**
 * Read a [Short] ordered in little endian order from a specific offset.
 */
public fun ByteArray.readShortLittleEndian(offset: Int): ReadValue<Short> {
    assertCanRead(offset, Short.SIZE_BYTES)

    val value = (get(0).toInt() or (get(1).toInt() and 0xFFFF shl 8)).toShort()

    return ReadValue(value, Short.SIZE_BYTES, offset + Short.SIZE_BYTES)
}

/**
 * Read a [Short] ordered in big endian order from a specific offset.
 */
public fun ByteArray.readShortBigEndian(offset: Int): ReadValue<Short> {
    assertCanRead(offset, Short.SIZE_BYTES)

    val value = (get(0).toInt() shl 8 or (get(1).toInt() and 0xFFFF)).toShort()

    return ReadValue(value, Short.SIZE_BYTES, offset + Short.SIZE_BYTES)
}

/**
 * Read a [Int] ordered in little endian order from a specific offset.
 */
public fun ByteArray.readIntLittleEndian(offset: Int): ReadValue<Int> {
    assertCanRead(offset, Int.SIZE_BYTES)

    val value = (get(0).toInt()
        or (get(1).toInt() and 0xFF shl 8)
        or (get(2).toInt() and 0xFF shl 16)
        or (get(3).toInt() and 0xFF shl 24))

    return ReadValue(value, Int.SIZE_BYTES, offset + Int.SIZE_BYTES)
}

/**
 * Read a [Int] ordered in big endian order from a specific offset.
 */
public fun ByteArray.readIntBigEndian(offset: Int): ReadValue<Int> {
    assertCanRead(offset, Int.SIZE_BYTES)

    val value = (get(0).toInt() shl 24
        or (get(1).toInt() and 0xFF shl 16)
        or (get(2).toInt() and 0xFF shl 8)
        or (get(3).toInt() and 0xFF))

    return ReadValue(value, Int.SIZE_BYTES, offset + Int.SIZE_BYTES)
}

/**
 * Read a [Long] ordered in little endian order from a specific offset.
 */
public fun ByteArray.readLongLittleEndian(offset: Int): ReadValue<Long> {
    assertCanRead(offset, Long.SIZE_BYTES)

    val value = (get(0).toLong()
        or (get(1).toLong() and 0xFF shl 8)
        or (get(2).toLong() and 0xFF shl 16)
        or (get(3).toLong() and 0xFF shl 24)
        or (get(4).toLong() and 0xFF shl 32)
        or (get(5).toLong() and 0xFF shl 40)
        or (get(6).toLong() and 0xFF shl 48)
        or (get(7).toLong() and 0xFF shl 56))

    return ReadValue(value, Long.SIZE_BYTES, offset + Long.SIZE_BYTES)
}

/**
 * Read a [Long] ordered in big endian order from a specific offset.
 */
public fun ByteArray.readLongBigEndian(offset: Int): ReadValue<Long> {
    assertCanRead(offset, Long.SIZE_BYTES)

    val value = (get(0).toLong() shl 56
        or (get(1).toLong() and 0xFF shl 48)
        or (get(2).toLong() and 0xFF shl 40)
        or (get(3).toLong() and 0xFF shl 32)
        or (get(4).toLong() and 0xFF shl 24)
        or (get(5).toLong() and 0xFF shl 16)
        or (get(6).toLong() and 0xFF shl 8)
        or (get(7).toLong() and 0xFF))

    return ReadValue(value, Long.SIZE_BYTES, offset + Long.SIZE_BYTES)
}

/**
 * Read a [Float] ordered in little endian order from a specific offset.
 */
public fun ByteArray.readFloatLittleEndian(offset: Int): ReadValue<Float> {
    val value = readIntLittleEndian(offset)

    return ReadValue(Float.fromBits(value.value), value.readBytes, value.nextOffset)
}

/**
 * Read a [Float] ordered in big endian order from a specific offset.
 */
public fun ByteArray.readFloatBigEndian(offset: Int): ReadValue<Float> {
    val value = readIntBigEndian(offset)

    return ReadValue(Float.fromBits(value.value), value.readBytes, value.nextOffset)
}

/**
 * Read a [Double] ordered in little endian order from a specific offset.
 */
public fun ByteArray.readDoubleLittleEndian(offset: Int): ReadValue<Double> {
    val value = readLongLittleEndian(offset)

    return ReadValue(Double.fromBits(value.value), value.readBytes, value.nextOffset)
}

/**
 * Read a [Double] ordered in big endian order from a specific offset.
 */
public fun ByteArray.readDoubleBigEndian(offset: Int): ReadValue<Double> {
    val value = readLongBigEndian(offset)

    return ReadValue(Double.fromBits(value.value), value.readBytes, value.nextOffset)
}

public fun ByteArray.readAsciiString(offset: Int, length: Int): ReadValue<String> {
    assertCanRead(offset, length)

    val nextOffset = offset + length
    val array = this
    val value = buildString {
        for (index in offset until nextOffset) {
            append(array[index].toInt().toChar())
        }
    }

    return ReadValue(value, length, nextOffset)
}

public fun ByteArray.readAsciiCString(offset: Int): ReadValue<String> {
    assertCanRead(offset, 0)

    var stopIndex = -1
    val array = this
    val value = buildString {
        for (index in offset until array.lastIndex) {
            val byte = array[index]
            if (byte == NUL) {
                stopIndex = index
                break
            }

            append(array[index].toInt().toChar())
        }
    }

    return ReadValue(value, value.length, stopIndex + 1)
}
