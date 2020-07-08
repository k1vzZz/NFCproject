package com.developer.nfcproject.httpclient

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal suspend fun Call.await(): Response {
    return suspendCancellableCoroutine { cancellableContinuation ->
        enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (cancellableContinuation.isCancelled) return
                cancellableContinuation.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                cancellableContinuation.resume(response)
            }
        })

        cancellableContinuation.invokeOnCancellation {
            try {
                cancel()
            } catch (e: Throwable) {

            }
        }
    }
}
