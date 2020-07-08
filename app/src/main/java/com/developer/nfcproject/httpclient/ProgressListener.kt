package com.developer.nfcproject.httpclient

interface ProgressListener {
    fun update(
        bytesRead: Long,
        contentLength: Long,
        done: Boolean
    )
}