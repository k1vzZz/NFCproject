package com.developer.nfcproject.ui.registration

import okhttp3.Request

internal class RegistrationRequestBuilder {
    private var serverUrl: String = ""
    private var deviceId: String = ""
    private var email: String = ""
    private var pin: String? = null

    fun serverUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }

    fun deviceId(deviceId: String) = apply { this.deviceId = deviceId }

    fun email(email: String) = apply { this.email = email }

    fun pin(pin: String?) = apply { this.pin = pin }

    fun build(): Request {
        val builder = Request.Builder()

        require(deviceId.isNotEmpty())
        require(email.isNotEmpty())
        var url =
            "$serverUrl/registration?device_id=$deviceId&email=$email"

        pin?.let {
            url += "&pin=$pin"
        }

        return builder.url(url).build()
    }
}