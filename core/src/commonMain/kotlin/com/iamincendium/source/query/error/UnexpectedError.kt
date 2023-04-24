package com.iamincendium.source.query.error

public data class UnexpectedError(val cause: Throwable) : SourceQueryError {
    override val message: String? = cause.message
}
