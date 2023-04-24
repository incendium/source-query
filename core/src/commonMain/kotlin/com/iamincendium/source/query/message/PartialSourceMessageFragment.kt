package com.iamincendium.source.query.message

/**
 * Partial fragment of a complete source packet.
 */
internal class PartialSourceMessageFragment(val header: MultiFragmentSourceMessageHeader, val content: ByteArray)
