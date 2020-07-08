package com.developer.nfcproject.models

import android.nfc.NdefMessage
import android.nfc.Tag


data class NfcModel(
    val tag: Tag,
    val messages: NfcDto?
)