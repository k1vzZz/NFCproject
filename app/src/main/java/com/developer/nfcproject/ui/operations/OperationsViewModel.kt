package com.developer.nfcproject.ui.operations

import android.app.Application
import android.nfc.NdefRecord
import android.nfc.Tag
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.developer.nfcproject.ServiceFailureResult
import com.developer.nfcproject.ServiceSuccessResult
import com.developer.nfcproject.models.NfcDto
import com.developer.nfcproject.models.NfcModel
import com.developer.nfcproject.models.OperationsModel
import com.developer.nfcproject.services.OperationsService
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.util.*

class OperationsViewModel(
    application: Application,
    private val operationsService: OperationsService
) : AndroidViewModel(application) {

    val isGeneralError = MutableLiveData<String>()

    val items: List<OperationsModel> = operationsService.getOperationsTest()

    val isProcessPay = MutableLiveData<Boolean>()

    val isSuccessPay = MutableLiveData<Boolean>()
    val isFailurePay = MutableLiveData<Boolean>()

    fun processPay(nfcModel: NfcModel) {
        viewModelScope.launch {
            isProcessPay.value = true
            isSuccessPay.value = false
            isFailurePay.value = false
            val result = operationsService.processPay(nfcModel)
            when (result) {
                is ServiceSuccessResult -> onResponse(result.result)
                is ServiceFailureResult -> isGeneralError.value = "Fail"
            }
            isProcessPay.value = false
        }
    }

    private fun onResponse(operationsModel: OperationsModel?) {
        isSuccessPay.value = true
    }

}