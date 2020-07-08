package com.developer.nfcproject.httpclient

import com.developer.nfcproject.BuildConfig
import okhttp3.EventListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.buffer
import javax.net.ssl.*

internal class NfcHttpClient(
    eventListenerFactory: EventListener.Factory? = null
) : NfcHttpClientInterface {
    private var client: OkHttpClient

    private val progressListenerList = ArrayList<ProgressListener>()

    init {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            logging.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        eventListenerFactory?.let {
            clientBuilder.eventListenerFactory(it)
        }

        client = clientBuilder
            .hostnameVerifier(HostnameVerifier { hostname, session ->
                hostname == session.peerHost
            })
            .addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                if (originalResponse.body != null) {
                    originalResponse.newBuilder()
                        .body(SpeedResponseBody(originalResponse.body!!))
                        .build()
                } else {
                    originalResponse
                }
            }
            .build()
    }

    override fun getOkHttpClient() = client

    override fun newCall(request: Request) = client.newCall(request)

    override fun addProgressListener(listener: ProgressListener) = progressListenerList.add(listener)
    override fun removeProgressListener(listener: ProgressListener) = progressListenerList.remove(listener)

    inner class SpeedResponseBody constructor(
        private val responseBody: ResponseBody
    ) : ResponseBody() {

        private var bufferedSource: BufferedSource? = null

        override fun contentLength() = responseBody.contentLength()

        override fun contentType() = responseBody.contentType()

        override fun source(): BufferedSource {
            if (bufferedSource == null) {
                bufferedSource = source(responseBody.source())
            }
            return bufferedSource!!
        }

        private fun source(bufferedSource: BufferedSource): BufferedSource {
            return object : ForwardingSource(bufferedSource) {

                var totalBytesRead = 0L

                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)

                    totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                    progressListenerList.forEach {
                        it.update(
                            totalBytesRead,
                            responseBody.contentLength(),
                            bytesRead == -1L
                        )
                    }

                    return bytesRead
                }

            }.buffer()
        }

    }

}