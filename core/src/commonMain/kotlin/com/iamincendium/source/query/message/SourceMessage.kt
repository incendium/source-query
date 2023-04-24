package com.iamincendium.source.query.message

/**
 * Base interface for defining a source message.
 *
 * For more information on the message structure, see the entry on the
 * [Valve Developer Community Wiki](https://developer.valvesoftware.com/wiki/Server_queries).
 */
internal sealed interface SourceMessage {
    val bytes: ByteArray
}
