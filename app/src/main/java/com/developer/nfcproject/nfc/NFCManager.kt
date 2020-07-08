package com.developer.nfcproject.nfc

import android.content.Context
import android.nfc.FormatException
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import com.developer.nfcproject.db.OperationsDao
import com.developer.nfcproject.models.NfcDto
import com.developer.nfcproject.services.OperationsService
import com.developer.nfcproject.utils.ByteUtils
import com.google.gson.Gson
import java.io.IOException

class NFCManager {

    private var ndef: Ndef? = null
    private val mimeType: String = ""

    val tagId: String?
        get() {
            ndef?.let {
                return ByteUtils.bytesToHexString(it.tag.id)
            }
            return null
        }

    @Throws(IOException::class, FormatException::class)
    fun writeData(tag: Tag, nfcDto: NfcDto): Boolean {
        val data = Gson().toJson(nfcDto)
        ndef = Ndef.get(tag)
        ndef?.let {
            val message = prepareMessageToWrite(data)
            it.connect()
            if (it.isConnected) {
                it.writeNdefMessage(message)
                return true
            }
        }
        return false
    }

    @Throws(IOException::class)
    private fun close() {
        ndef?.close()
    }

    fun prepareMessageToWrite(tagData: String): NdefMessage {
        val message: NdefMessage
        val typeBytes = mimeType.toByteArray()
        val payload = tagData.toByteArray()
        val record1 = NdefRecord(NdefRecord.TNF_MIME_MEDIA, typeBytes, null, payload)
        message = NdefMessage(record1)
        return message
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: NFCManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: NFCManager().also {
                    instance = it
                }
            }
    }
}