package com.developer.nfcproject.httpclient

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

internal class NfcRequestBuilder {
    private var serverUrl: String = ""
    private var audio: ByteArray? = null
    private var deviceId: String = ""
    private var extendedResponse: Boolean = false
    private var text: String = ""
    private var isResetContext: Boolean = false
    private var skipTTS: Boolean = false

    fun serverUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }

    fun audio(audio: ByteArray) = apply { this.audio = audio }

    fun text(text: String) = apply { this.text = text }

    fun deviceId(deviceId: String) = apply { this.deviceId = deviceId }

    fun resetContext(isResetContext: Boolean) = apply { this.isResetContext = isResetContext }

    fun extendedResponse(extendedResponse: Boolean) =
        apply { this.extendedResponse = extendedResponse }

    fun skipTTS(skipTTS: Boolean) =
        apply { this.skipTTS = skipTTS }

    fun build(): Request {
        val builder = Request.Builder()

        require(deviceId.isNotEmpty())

        var url = "$serverUrl/en/put_audio?"
        url += "device_id=$deviceId"
        url += "&trigger_type=2"
        url += "&with_debug=True"
        url += "&skip_tts=$skipTTS"
        url += "&extended_response=$extendedResponse"
        url += "&reset_context=$isResetContext"

        if (text.isNotEmpty()) {
            url += "&stt_utterance=$text"
            builder.post(ByteArray(0).toRequestBody("audio/vnd.wave".toMediaType()))
        } else if (audio != null) {
            builder.post(audio!!.toRequestBody("audio/vnd.wave".toMediaType()))
        }

        builder.url(url)

        return builder.build()
    }
}