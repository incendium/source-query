package com.iamincendium.source.query.error

import com.iamincendium.source.query.message.SourceResponseMessage
import kotlin.reflect.KClass

public sealed interface ConvertError : ClientError {
    public data class UnsupportedMessageType internal constructor(
        private val sourceMessageType: KClass<out SourceResponseMessage>,
    ) : ClientError {
        override val message: String =
            "Attempted to convert unsupported response message type: ${sourceMessageType.simpleName}"
    }
}
