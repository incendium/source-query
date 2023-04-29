package com.iamincendium.source.query.message

import com.iamincendium.source.query.test.util.byteArrayFromInts
import io.kotest.core.spec.style.DescribeSpec

private fun rulesResponseMessage(vararg values: Int) =
    RulesResponseMessage(SingleFragmentMessageHeader, byteArrayFromInts(*values))

class RulesResponseMessageTest : DescribeSpec({
    xdescribe("RulesResponseMessage") {

    }
})
