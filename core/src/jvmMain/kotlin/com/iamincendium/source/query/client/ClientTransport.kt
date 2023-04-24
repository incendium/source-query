package com.iamincendium.source.query.client

import com.github.michaelbull.result.Result
import com.iamincendium.source.query.error.SourceQueryError

internal actual class ClientTransport actual constructor(address: String, port: Int) {
    internal actual suspend fun send(payload: ByteArray): Result<Unit, SourceQueryError> {
        TODO("Not yet implemented")
    }

    internal actual suspend fun receive(): Result<ByteArray, SourceQueryError> {
        TODO("Not yet implemented")
    }
}
