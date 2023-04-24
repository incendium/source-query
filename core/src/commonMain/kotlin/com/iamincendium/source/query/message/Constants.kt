package com.iamincendium.source.query.message

internal const val DEFAULT_SOURCE_CHALLENGE: Int = -1

internal const val HEADER_SINGLE: Int = -1
internal const val HEADER_FRAGMENT: Int = -2
internal const val HEADER_NO_SIZE: Short = -1
internal const val HEADER_NOT_COMPRESSED: Int = -1

internal const val EDF_GAME_PORT = 0x80.toByte()
internal const val EDF_SERVER_ID = 0x10.toByte()
internal const val EDF_SPECTATOR_INFO = 0x40.toByte()
internal const val EDF_TAGS = 0x20.toByte()
internal const val EDF_GAME_ID = 0x01.toByte()

internal const val UNKNOWN_APP_ID = -1L

@Suppress("MagicNumber")
internal val APP_ID_NO_SIZE = setOf(
    215L,
    17550L,
    17700L,
    240L,
)
