package com.developer.nfcproject.httpclient

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

interface NfcHttpClientInterface {
    fun getOkHttpClient(): OkHttpClient
    fun newCall(request: Request): Call
    fun addProgressListener(listener: ProgressListener): Boolean
    fun removeProgressListener(listener: ProgressListener): Boolean
}