@file:Suppress("MagicNumber", "TooManyFunctions")
package com.iamincendium.source.query.util

/**
 * Convert this [Short] to the corresponding bytes (as little endian).
 */
internal fun Short.toByteArrayLittleEndian(): ByteArray = toByteArrayLittleEndian(ByteArray(Short.SIZE_BYTES))

/**
 * Convert this [Short] to the corresponding bytes (as little endian).
 */
internal fun Short.toByteArrayLittleEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Short.SIZE_BYTES)

    array[offset]     = this.toByte()
    array[offset + 1] = (this.toInt() shr 8).toByte()

    return array
}

/**
 * Convert this [Short] to the corresponding bytes (as big endian).
 */
internal fun Short.toByteArrayBigEndian(): ByteArray = toByteArrayBigEndian(ByteArray(Short.SIZE_BYTES))

/**
 * Convert this [Short] to the corresponding bytes (as big endian).
 */
internal fun Short.toByteArrayBigEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Short.SIZE_BYTES)

    array[offset] = (this.toInt() shr 8).toByte()
    array[offset + 1] = this.toByte()

    return array
}

/**
 * Convert this [Int] to the corresponding bytes (as little endian).
 */
internal fun Int.toByteArrayLittleEndian(): ByteArray = toByteArrayLittleEndian(ByteArray(Int.SIZE_BYTES))

/**
 * Convert this [Int] to the corresponding bytes (as little endian).
 */
internal fun Int.toByteArrayLittleEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Int.SIZE_BYTES)

    array[offset]     = this.toByte()
    array[offset + 1] = (this shr 8).toByte()
    array[offset + 2] = (this shr 16).toByte()
    array[offset + 3] = (this shr 24).toByte()

    return array
}

/**
 * Convert this [Int] to the corresponding bytes (as big endian).
 */
internal fun Int.toByteArrayBigEndian(): ByteArray = toByteArrayBigEndian(ByteArray(Int.SIZE_BYTES))

/**
 * Convert this [Int] to the corresponding bytes (as big endian).
 */
internal fun Int.toByteArrayBigEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Int.SIZE_BYTES)

    array[offset]     = (this shr 24).toByte()
    array[offset + 1] = (this shr 16).toByte()
    array[offset + 2] = (this shr 8).toByte()
    array[offset + 3] = this.toByte()

    return array
}

/**
 * Convert this [Long] to the corresponding bytes (as little endian).
 */
internal fun Long.toByteArrayLittleEndian(): ByteArray = toByteArrayLittleEndian(ByteArray(Long.SIZE_BYTES))

/**
 * Convert this [Long] to the corresponding bytes (as little endian) and write it into the specified byte array.
 */
internal fun Long.toByteArrayLittleEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Long.SIZE_BYTES)

    array[offset]     = this.toByte()
    array[offset + 1] = (this shr 8).toByte()
    array[offset + 2] = (this shr 16).toByte()
    array[offset + 3] = (this shr 24).toByte()
    array[offset + 4] = (this shr 32).toByte()
    array[offset + 5] = (this shr 40).toByte()
    array[offset + 6] = (this shr 48).toByte()
    array[offset + 7] = (this shr 56).toByte()

    return array
}

/**
 * Convert this [Long] to the corresponding bytes (as big endian).
 */
internal fun Long.toByteArrayBigEndian(): ByteArray = toByteArrayBigEndian(ByteArray(Long.SIZE_BYTES))

/**
 * Convert this [Long] to the corresponding bytes (as big endian).
 */
internal fun Long.toByteArrayBigEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Long.SIZE_BYTES)

    array[offset]     = (this shr 56).toByte()
    array[offset + 1] = (this shr 48).toByte()
    array[offset + 2] = (this shr 40).toByte()
    array[offset + 3] = (this shr 32).toByte()
    array[offset + 4] = (this shr 24).toByte()
    array[offset + 5] = (this shr 16).toByte()
    array[offset + 6] = (this shr 8).toByte()
    array[offset + 7] = this.toByte()

    return array
}

/**
 * Convert this [Float] to the corresponding bytes (as little endian).
 */
internal fun Float.toByteArrayLittleEndian(): ByteArray = toByteArrayLittleEndian(ByteArray(Float.SIZE_BYTES))

/**
 * Convert this [Float] to the corresponding bytes (as little endian).
 */
internal fun Float.toByteArrayLittleEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Float.SIZE_BYTES)

    return this.toBits().toByteArrayLittleEndian(array, offset)
}

/**
 * Convert this [Int] to the corresponding bytes (as big endian).
 */
internal fun Float.toByteArrayBigEndian(): ByteArray = toByteArrayBigEndian(ByteArray(Float.SIZE_BYTES))

/**
 * Convert this [Int] to the corresponding bytes (as big endian).
 */
internal fun Float.toByteArrayBigEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Float.SIZE_BYTES)

    return this.toBits().toByteArrayBigEndian(array, offset)
}

/**
 * Convert this [Double] to the corresponding bytes (as little endian).
 */
internal fun Double.toByteArrayLittleEndian(): ByteArray = toByteArrayLittleEndian(ByteArray(Double.SIZE_BYTES))

/**
 * Convert this [Double] to the corresponding bytes (as little endian) and write it into the specified byte array.
 */
internal fun Double.toByteArrayLittleEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Double.SIZE_BYTES)

    return this.toBits().toByteArrayLittleEndian(array, offset)
}

/**
 * Convert this [Double] to the corresponding bytes (as big endian).
 */
internal fun Double.toByteArrayBigEndian(): ByteArray = toByteArrayBigEndian(ByteArray(Double.SIZE_BYTES))

/**
 * Convert this [Double] to the corresponding bytes (as big endian).
 */
internal fun Double.toByteArrayBigEndian(array: ByteArray, offset: Int = 0): ByteArray {
    array.assertCanWrite(offset, Double.SIZE_BYTES)

    return this.toBits().toByteArrayBigEndian(array, offset)
}

/**
 * Casts an array of integers into an array of bytes.
 */
internal fun Array<Int>.toByteArray() = map { it.toByte() }.toTypedArray()

/**
 * Casts an array of integers into an array of bytes.
 */
internal fun IntArray.toByteArray() = map { it.toByte() }.toByteArray()
