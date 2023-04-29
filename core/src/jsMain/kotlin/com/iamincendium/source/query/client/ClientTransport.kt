package com.iamincendium.source.query.client

import com.github.michaelbull.result.Result
import com.iamincendium.source.query.error.SourceQueryError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okio.Closeable

internal actual fun defaultScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)

internal actual class ClientTransport actual constructor(
    address: String,
    port: Int,
    scope: CoroutineScope,
) : Closeable {
    internal actual suspend fun send(payload: ByteArray): Result<Unit, SourceQueryError> {
        TODO("Not yet implemented")
    }

    internal actual suspend fun receive(): Result<ByteArray, SourceQueryError> {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
