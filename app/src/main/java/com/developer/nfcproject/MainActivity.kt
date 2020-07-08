package com.developer.nfcproject

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.developer.nfcproject.models.NfcDto
import com.developer.nfcproject.models.NfcModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private var adapter: NfcAdapter? = null
    private var baseViewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        baseViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        initNfcAdapter()
    }

    private fun initNfcAdapter() {
        adapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    private fun enableNfcForegroundDispatch() {
        try {
            val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            adapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
        } catch (ex: IllegalStateException) {
            Log.e("MainActivity", "Error enabling NFC foreground dispatch", ex)
        }
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }

    private fun disableNfcForegroundDispatch() {
        try {
            adapter?.disableForegroundDispatch(this)
        } catch (ex: IllegalStateException) {
            Log.e("MainActivity", "Error disabling NFC foreground dispatch", ex)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        var messages: List<NdefMessage>? = null
        intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
            messages = rawMessages.map { it as NdefMessage }
        }
        val data = messages?.get(0)?.records?.get(0)?.payload?.let {
            val str = String(it)
            Gson().fromJson(str, NfcDto::class.java)
        }
        val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as Tag
        val nfcModel = NfcModel(tag, data)
        baseViewModel?.nfcIntent?.value = nfcModel
    }
}