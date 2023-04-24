package com.iamincendium.source.query.client

import com.github.michaelbull.result.Result
import com.iamincendium.source.query.error.SourceQueryError

internal expect class ClientTransport(address: String, port: Int) {
    internal suspend fun send(payload: ByteArray): Result<Unit, SourceQueryError>
    internal suspend fun receive(): Result<ByteArray, SourceQueryError>
}
