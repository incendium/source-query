package com.iamincendium.source.query.message

import com.iamincendium.source.query.util.readNullTerminatedUtf8String
import okio.Buffer

/**
 * `S2A_RULES`
 *
 * Packet received from a Source server with the rules list as key->value pairs.
 *
 * | Data                  | C Type   | Kotlin Type   | Value                                             |
 * | --------------------- | -------- | ------------- | ------------------------------------------------- |
 * | Header                | `byte`   | [Byte]        | 'D' (0x44)                                        |
 * | Rules                 | `short`  | [Short]       | Number of rules in the response.                  |
 *
 * Each rule will have the following entry in the response:
 *
 * | Data     | C Type      | Kotlin Type | Value                                                      |
 * | -------- | ----------- | ----------- | ---------------------------------------------------------- |
 * | Name     | `string`    | [String]    | Name of the rule.                                          |
 * | Value    | `string`    | [String]    | Value of the rule.                                         |
 *
 * @see RulesRequestMessage
 */
internal class RulesResponseMessage(
    header: MessageHeader,
    content: ByteArray,
) : SourceResponseMessage(MessageType.Response.PlayerResponse, header, content) {
    private val buffer = Buffer().also { it.write(content) }

    val rules: Map<String, String> = buildMap {
        repeat(buffer.readByte().toInt()) {
            this[buffer.readNullTerminatedUtf8String()] = buffer.readNullTerminatedUtf8String()
        }
    }
}
