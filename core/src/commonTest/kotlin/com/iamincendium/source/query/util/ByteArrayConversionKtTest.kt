package com.iamincendium.source.query.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.iamincendium.source.query.test.util.byteArrayFromInts
import io.kotest.core.spec.style.DescribeSpec

class ByteArrayConversionKtTest : DescribeSpec({
    describe("Short.toByteArrayLittleEndian") {
        it("should create byte array for long") {
            assertThat(1.toShort().toByteArrayLittleEndian())
                .isEqualTo(byteArrayOf(0x01, 0x00))
        }
    }
    describe("Short.toByteArrayBigEndian") {
        it("should create byte array for long") {
            assertThat(1.toShort().toByteArrayBigEndian())
                .isEqualTo(byteArrayOf(0x00, 0x01))
        }
    }
    describe("Int.toByteArrayLittleEndian") {
        it("should create byte array for long") {
            assertThat(1.toByteArrayLittleEndian())
                .isEqualTo(byteArrayOf(0x01, 0x00, 0x00, 0x00))
        }
    }
    describe("Int.toByteArrayBigEndian") {
        it("should create byte array for long") {
            assertThat(1.toByteArrayBigEndian())
                .isEqualTo(byteArrayOf(0x00, 0x00, 0x00, 0x01))
        }
    }
    describe("Long.toByteArrayLittleEndian") {
        it("should create byte array for long") {
            assertThat(1L.toByteArrayLittleEndian())
                .isEqualTo(byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
        }
    }
    describe("Long.toByteArrayBigEndian") {
        it("should create byte array for long") {
            assertThat(1L.toByteArrayBigEndian())
                .isEqualTo(byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01))
        }
    }
    describe("Float.toByteArrayLittleEndian") {
        it("should create byte array for float") {
            assertThat(1.0F.toByteArrayLittleEndian())
                .isEqualTo(byteArrayFromInts(0x00, 0x00, 0x80, 0x3F))
        }
    }
    describe("Float.toByteArrayBigEndian") {
        it("should create byte array for float") {
            assertThat(1.0F.toByteArrayBigEndian())
                .isEqualTo(byteArrayFromInts(0x3F, 0x80, 0x00, 0x00))
        }
    }
    describe("Double.toByteArrayLittleEndian") {
        it("should create byte array for double") {
            assertThat(1.0.toByteArrayLittleEndian())
                .isEqualTo(byteArrayFromInts(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF0, 0x3F))
        }
    }
    describe("Double.toByteArrayBigEndian") {
        it("should create byte array for double") {
            assertThat(1.0.toByteArrayBigEndian())
                .isEqualTo(byteArrayFromInts(0x3F, 0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
        }
    }
    describe("Array<Int>.toByteArray") {

    }
    describe("IntArray.toByteArray") {

    }
})
