package com.developer.nfcproject.models

import com.google.gson.annotations.SerializedName

data class NfcDto (
    @SerializedName("transport_type")
    val transportType: String,
    @SerializedName("transport_number")
    val transportNumber: String
)