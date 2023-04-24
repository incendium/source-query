package com.iamincendium.source.query.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import io.kotest.core.spec.style.DescribeSpec

class ByteArrayExtensionsKtTest : DescribeSpec({
    describe("ByteArray.readByte") {
        it("should throw an exception on an invalid offset") {
            val array = byteArrayOf()

            assertThat { array.readByte(0) }
                .isFailure()
                .isInstanceOf(IndexOutOfBoundsException::class)
        }

        it("should a read byte from an array") {
            val array = byteArrayOf(0x00)

            val value = array.readByte(0)
            assertThat(value.value).isEqualTo(0x00)
            assertThat(value.readBytes).isEqualTo(1)
            assertThat(value.nextOffset).isEqualTo(1)
        }
    }
    describe("ByteArray.readShort") {
        context("with little endian") {
            it("should throw an exception on an invalid offset") {
                val array = byteArrayOf()

                assertThat { array.readShortLittleEndian(0) }
                    .isFailure()
                    .isInstanceOf(IndexOutOfBoundsException::class)
            }

            it("should read a short from an array") {
                val array = byteArrayOf(0x01, 0x00)

                val value = array.readShortLittleEndian(0)
                assertThat(value.value).isEqualTo(1.toShort())
            }
        }
        context("with big endian") {
            it("should throw an exception on an invalid offset") {
                val array = byteArrayOf()

                assertThat { array.readShortBigEndian(0) }
                    .isFailure()
                    .isInstanceOf(IndexOutOfBoundsException::class)
            }

            it("should read a short from an array") {
                val array = byteArrayOf(0x00, 0x01)

                val value = array.readShortBigEndian(0)
                assertThat(value.value).isEqualTo(1.toShort())
            }
        }
    }
    describe("ByteArray.readInt") {
        context("with little endian") {
            it("should throw an exception on an invalid offset") {
                val array = byteArrayOf()

                assertThat { array.readIntLittleEndian(0) }
                    .isFailure()
                    .isInstanceOf(IndexOutOfBoundsException::class)
            }

            it("should read an int from an array") {
                val array = byteArrayOf(0x01, 0x00, 0x00, 0x00)

                val value = array.readIntLittleEndian(0)
                assertThat(value.value).isEqualTo(1)
            }
        }
        context("with big endian") {
            it("should throw an exception on an invalid offset") {
                val array = byteArrayOf()

                assertThat { array.readIntBigEndian(0) }
                    .isFailure()
                    .isInstanceOf(IndexOutOfBoundsException::class)
            }

            it("should read an int from an array") {
                val array = byteArrayOf(0x00, 0x00, 0x00, 0x01)

                val value = array.readIntBigEndian(0)
                assertThat(value.value).isEqualTo(1)
            }
        }
    }
    describe("ByteArray.readLong") {
        context("with little endian") {
            it("should throw an exception on an invalid offset") {
                val array = byteArrayOf()

                assertThat { array.readLongLittleEndian(0) }
                    .isFailure()
                    .isInstanceOf(IndexOutOfBoundsException::class)
            }

            it("should read a long from an array") {
                val array = byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)

                val value = array.readLongLittleEndian(0)
                assertThat(value.value).isEqualTo(1L)
            }
        }
        context("with big endian") {
            it("should throw an exception on an invalid offset") {
                val array = byteArrayOf()

                assertThat { array.readLongBigEndian(0) }
                    .isFailure()
                    .isInstanceOf(IndexOutOfBoundsException::class)
            }

            it("should read a long from an array") {
                val array = byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01)

                val value = array.readLongBigEndian(0)
                assertThat(value.value).isEqualTo(1L)
            }
        }
    }
    describe("ByteArray.readAsciiString") {
        it("should read a fixed length ASCII string") {
            val expected = "this was a triumph"
            val array = expected.toByteArray(Charsets.US_ASCII)

            val value = array.readAsciiString(0, expected.length)
            assertThat(value.value).isEqualTo(expected)
        }
    }
    describe("ByteArray.readAsciiCString") {
        it("should read a NUL-terminated ASCII string") {
            val expected = "this was a triumph"
            val array = (expected + "\u0000").toByteArray(Charsets.US_ASCII)

            val value = array.readAsciiCString(0)
            assertThat(value.value).isEqualTo(expected)
        }
    }
})
