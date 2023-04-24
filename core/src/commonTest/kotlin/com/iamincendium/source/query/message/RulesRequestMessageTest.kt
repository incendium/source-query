package com.iamincendium.source.query.message

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.iamincendium.source.query.test.util.byteArrayFromInts
import io.kotest.core.spec.style.DescribeSpec

class RulesRequestMessageTest : DescribeSpec({
    describe("RulesRequestMessage") {
        it("should create the proper byte layout") {
            val request = RulesRequestMessage(0x00_FF_00_FF)
            val expected = byteArrayFromInts(0xFF, 0xFF, 0xFF, 0xFF, 0x56, 0xFF, 0x00, 0xFF, 0x00)

            assertThat(request.bytes).isEqualTo(expected)
        }
    }
})
