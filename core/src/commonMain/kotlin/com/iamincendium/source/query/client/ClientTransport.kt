package com.iamincendium.source.query.client

import com.github.michaelbull.result.Result
import com.iamincendium.source.query.error.SourceQueryError
import kotlinx.coroutines.CoroutineScope
import okio.Closeable

internal expect fun defaultScope(): CoroutineScope

internal expect class ClientTransport(
    address: String,
    port: Int,
    scope: CoroutineScope = defaultScope(),
) : Closeable {
    internal suspend fun send(payload: ByteArray): Result<Unit, SourceQueryError>
    internal suspend fun receive(): Result<ByteArray, SourceQueryError>
}
