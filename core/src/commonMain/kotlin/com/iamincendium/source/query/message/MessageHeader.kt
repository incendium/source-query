package com.iamincendium.source.query.message

import okio.Buffer

internal sealed interface MessageHeader {
    val identifier: Int
    val bytes: ByteArray
    val size: Int
}

/**
 * Header indicating that the packet came from a single request/response.
 *
 * | Data   | C Type | Kotlin Type | Comment         |
 * | ------ | ------ | ----------- | --------------- |
 * | Header | `long` | [Int]       | -1 (0xFFFFFFFF) |
 */
internal object SingleFragmentMessageHeader : MessageHeader {
    override val identifier: Int = HEADER_SINGLE
    override val bytes: ByteArray = Buffer().also { it.writeIntLe(identifier) }.readByteArray()
    override val size: Int = bytes.size
}

/**
 * Header indicating that the packet came from multiple requests/responses in the Source format.
 *
 * | Data   | C Type  | Kotlin Type | Comment                                      |
 * | ------ | ------- | ----------- | -------------------------------------------- |
 * | Header | `long`  | [Int]       | -2 (0xFFFFFFFE)                              |
 * | ID     | `long`  | [Int]       | Unique number assigned by server per answer. However, if the most significant bit is 1, then the response was compressed with bzip2 before being cut and sent. Refer to compression procedure below. |
 * | Total  | `byte`  | [Byte]      | The total number of packets in the response. |
 * | Number | `byte`  | [Byte]      | The number of the packet. Starts at 0.       |
 * | Size   | `short` | [Short]     | (Orange Box Engine and above only.) Maximum size of packet before packet switching occurs. The default value is 1248 bytes (0x04E0), but the server administrator can decrease this. For older engine versions: the maximum and minimum size of the packet was unchangeable. AppIDs which are known not to contain this field: 215, 17550, 17700, and 240 when protocol = 7. |
 *
 * If compression is enabled, the following values are also included in the first packet:
 *
 * | Data      | C Type | Kotlin Type | Comment                                             |
 * | --------- | ------ | ----------- | --------------------------------------------------- |
 * | Size      | `long` | [Int]       | Size of the whole response once it is decompressed. |
 * | CRC32 sum | `long` | [Int]       | CRC32 checksum of uncompressed response.            |
 */
@Suppress("MagicNumber", "ForbiddenComment")
internal class MultiFragmentSourceMessageHeader(buffer: Buffer, appId: Long = UNKNOWN_APP_ID) : MessageHeader {
    private val peekBuffer = buffer.peek()

    // FIXME: Need to find some sort of test data for this as I have yet to run into a source server implementation that
    //  actually uses this packet/header format.
    override val identifier: Int = HEADER_FRAGMENT

    val id: Int = peekBuffer.readIntLe()
    val total: Byte = peekBuffer.readByte()
    val number: Byte = peekBuffer.readByte()
    val packetSize: Short
    val isCompressed: Boolean
    val uncompressedSize: Int
    val uncompressedCrc32Sum: Int

    override val bytes: ByteArray
    override val size: Int

    init {
        packetSize = if (appId !in APP_ID_NO_SIZE) {
            peekBuffer.readShortLe()
        } else {
            HEADER_NO_SIZE
        }

        isCompressed = (this.id and 1) == 1
        if (isCompressed) {
            uncompressedSize = peekBuffer.readIntLe()
            uncompressedCrc32Sum = peekBuffer.readIntLe()
        } else {
            uncompressedSize = HEADER_NOT_COMPRESSED
            uncompressedCrc32Sum = HEADER_NOT_COMPRESSED
        }

        // TODO: See if there is a better way to do this. Judging from the Okio API though,
        //  there does not seem to be a way to access the position of the peekBUffer.
        var byteCount = Int.SIZE_BYTES + (Byte.SIZE_BYTES * 2)
        if (appId !in APP_ID_NO_SIZE) {
            byteCount += Short.SIZE_BYTES
        }
        if (isCompressed) {
            byteCount += Int.SIZE_BYTES * 2
        }

        bytes = buffer.readByteArray(byteCount.toLong())
        size = bytes.size
    }
}
