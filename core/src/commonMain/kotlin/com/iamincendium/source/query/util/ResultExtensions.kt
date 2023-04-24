package com.iamincendium.source.query.util

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.iamincendium.source.query.error.SourceQueryError
import com.iamincendium.source.query.error.UnexpectedError

/**
 * Similar to [runCatching] except the [Result] error type is remapped to a generic subclass of [SourceQueryError].
 */
internal inline fun <T : Any> runOrError(block: () -> T): Result<T, SourceQueryError> =
    runCatching(block).mapError { UnexpectedError(it) }
