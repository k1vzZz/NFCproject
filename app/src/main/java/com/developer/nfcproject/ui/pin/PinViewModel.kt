package com.developer.nfcproject.ui.pin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developer.nfcproject.utils.AppPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class PinViewModel(
    application: Application,
    private val appPreferences: AppPreferences
) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance().also {
        it.useAppLanguage()
    }

    var phone: String? = null
    var verificationId: String? = null

    val goToWelcome = MutableLiveData(false)

    val isGeneralError = MutableLiveData<String>()

    fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    appPreferences.setPhoneNumber(phone!!)
                    goToWelcome.value = true
                } else {
                    isGeneralError.value = "Invalid pin code"
                }
            }
    }
}