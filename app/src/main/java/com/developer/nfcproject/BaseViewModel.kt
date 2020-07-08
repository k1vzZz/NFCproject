package com.developer.nfcproject

import android.app.Application
import android.nfc.Tag
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developer.nfcproject.models.NfcModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var nfcIntent = MutableLiveData<NfcModel>()
}