package com.iamincendium.source.query.message

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.iamincendium.source.query.test.util.byteArrayFromInts
import io.kotest.core.spec.style.DescribeSpec

class PlayerRequestMessageTest : DescribeSpec({
    describe("PlayerRequestMessage") {
        it("should create the proper byte layout") {
            val request = PlayerRequestMessage(150)
            val expected = byteArrayFromInts(0xFF, 0xFF, 0xFF, 0xFF, 0x55, 0x96, 0x00, 0x00, 0x00)

            assertThat(request.bytes).isEqualTo(expected)
        }
    }
})
