package com.iamincendium.source.query.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.iamincendium.source.query.test.util.byteArrayFromInts
import io.kotest.core.spec.style.DescribeSpec
import okio.Buffer

class BufferExtensionsKtTest : DescribeSpec({
    describe("Buffer.readNullTerminatedUtf8String") {
        it("should read single string") {
            val string = "hello, world!"
            val payload = (string + "\u0000").encodeToByteArray()

            val buffer = Buffer()
            buffer.write(payload)

            assertThat(buffer.readNullTerminatedUtf8String()).isEqualTo(string)
        }
        it("should read single offset string") {
            val string = "hello, world!"
            val paddingSize = 25
            val payload = (string + "\u0000").encodeToByteArray()

            val buffer = Buffer()
            repeat(paddingSize) {
                buffer.writeByte(0x00)
            }
            buffer.write(payload)
            buffer.skip(paddingSize.toLong())

            assertThat(buffer.readNullTerminatedUtf8String()).isEqualTo(string)
        }
        it("should read multiple strings") {
            val firstString = "hello,"
            val secondString = "world!"
            val payload = (firstString + "\u0000" + secondString + "\u0000").encodeToByteArray()

            val buffer = Buffer()
            buffer.write(payload)

            assertThat(buffer.readNullTerminatedUtf8String()).isEqualTo(firstString)
            assertThat(buffer.readNullTerminatedUtf8String()).isEqualTo(secondString)
        }
    }
    describe("Buffer.readFloatLe") {
        it("should read a float in little endian order") {
            val buffer = Buffer()
            buffer.write(byteArrayFromInts(0x00, 0x00, 0x80, 0x3F))

            assertThat(buffer.readFloatLe()).isEqualTo(1.0F)
        }
    }
    describe("Buffer.readFloat") {
        it("should read a float in big endian order") {
            val buffer = Buffer()
            buffer.write(byteArrayFromInts(0x3F, 0x80, 0x00, 0x00))

            assertThat(buffer.readFloat()).isEqualTo(1.0F)
        }
    }
    describe("Buffer.readDoubleLe") {
        it("should read a double in little endian order") {
            val buffer = Buffer()
            buffer.write(byteArrayFromInts(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF0, 0x3F))

            assertThat(buffer.readDoubleLe()).isEqualTo(1.0)
        }
    }
    describe("Buffer.readDouble") {
        it("should read a double in big endian order") {
            val buffer = Buffer()
            buffer.write(byteArrayFromInts(0x3F, 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))

            assertThat(buffer.readDouble()).isEqualTo(1.0)
        }
    }
})
