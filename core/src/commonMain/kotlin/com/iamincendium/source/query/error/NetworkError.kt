package com.iamincendium.source.query.error

public sealed interface NetworkError : SourceQueryError {
    public object ConnectionTimedOut
    public object ConnectionRejected
}
