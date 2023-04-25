package com.iamincendium.source.query.util

private const val HEX_DUMP_OFFSET = 0L
private const val HEX_DUMP_INDEX = 0
private const val HEX_DUMP_ROW_WIDTH = 24
private const val HEX_DUMP_SEPARATOR = " | "

/**
 * Converts a byte array into a formatted hex dump.
 */
internal fun ByteArray.toHexDump(
    offset: Long = HEX_DUMP_OFFSET,
    index: Int = HEX_DUMP_INDEX,
    rowWidth: Int = HEX_DUMP_ROW_WIDTH,
    separator: String = HEX_DUMP_SEPARATOR
): String {
    val output = StringBuilder()

    hexDump(output, offset, index, rowWidth, separator)

    return output.toString()
}

/**
 * Dump the contents of this byte array to an appendable object. The output is
 * formatted for human inspection, with a hexadecimal offset followed by the
 * hexadecimal values of the specified number of bytes of data and the
 * printable ASCII characters (if any) that those bytes represent printed per
 * each line of output.
 *
 * The offset argument specifies the start offset of the data array
 * within a larger entity like a file or an incoming stream. For example,
 * if the data array contains the third kibibyte of a file, then the
 * offset argument should be set to 2048. The offset value printed
 * at the beginning of each line indicates where in that larger entity
 * the first byte on that line is located.
 *
 * All bytes between the given index (inclusive) and the end of the
 * data array are dumped.
 *
 * Note: This function has been copied from Apache commons-io and ported to
 * Kotlin for multi-platform compatibility.
 *
 * @param offset  offset of the byte array within a larger entity
 * @param index initial index into the byte array
 */
internal fun ByteArray.hexDump(
    output: Appendable,
    offset: Long = HEX_DUMP_OFFSET,
    index: Int = HEX_DUMP_INDEX,
    rowWidth: Int = HEX_DUMP_ROW_WIDTH,
    separator: String = HEX_DUMP_SEPARATOR
) {
    require(index in 0 until size) { "illegal index: $index into array of length $size" }

    var rowOffset = offset + index
    for (i in index until size step rowWidth) {
        val rowSize = minOf(size - i, rowWidth)

        output.append(rowOffset.toHexString())
            .append(separator)

        for (j in 0 until rowWidth) {
            if (j < rowSize) {
                output.append(this[j + i].toUByte().toHexString())
            } else {
                output.append("  ")
            }

            if (j < rowWidth - 1) {
                output.append(" ")
            }
        }

        output.append(separator)

        for (j in 0 until rowSize) {
            output.append(this[j + i].toCharOr('.'))
        }

        output.append("\n")

        rowOffset += rowSize
    }
}

/**
 * Convert a byte to a two character hexidecimal string.
 */
internal fun Byte.toHexString() = toString(16).padStart(Byte.SIZE_BYTES * 2, '0').uppercase()

/**
 * Convert a byte to a two character hexidecimal string.
 */
internal fun UByte.toHexString() = toString(16).padStart(UByte.SIZE_BYTES * 2, '0').uppercase()

/**
 * Convert an integer to a four character hexidecimal string.
 */
internal fun Int.toHexString() = toString(16).padStart(Int.SIZE_BYTES * 2, '0').uppercase()

/**
 * Convert a long to an eight character hexidecimal string.
 */
internal fun Long.toHexString() = toString(16).padStart(Long.SIZE_BYTES * 2, '0').uppercase()

/**
 * Determine whether or not a byte is printable.
 */
private fun Byte.isPrintable(): Boolean = this >= ' '.code.toByte() && this < 127

/**
 * Convert a byte to a character if it is printable, otherwise return the default character.
 *
 * @param defaultValue the char to use if the byte is not printable
 */
private fun Byte.toCharOr(defaultValue: Char) = if (isPrintable()) this.toInt().toChar() else defaultValue
