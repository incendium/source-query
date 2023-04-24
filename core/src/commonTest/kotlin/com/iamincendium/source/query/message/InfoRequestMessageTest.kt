package com.iamincendium.source.query.message

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.iamincendium.source.query.test.util.byteArrayFromInts
import io.kotest.core.spec.style.DescribeSpec

class InfoRequestMessageTest : DescribeSpec({
    describe("InfoRequestMessage") {
        it("should create the proper byte layout") {
            val request = InfoRequestMessage
            val expected = byteArrayFromInts(0xFF, 0xFF, 0xFF, 0xFF, 0x54) + INFO_REQUEST_CONTENT

            assertThat(request.bytes).isEqualTo(expected)
        }
    }
})
