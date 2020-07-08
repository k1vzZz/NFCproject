package com.developer.nfcproject.ui.registration

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developer.nfcproject.services.RegistrationService
import com.developer.nfcproject.utils.AppPreferences
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class RegistrationViewModel(
    application: Application,
    private val registrationService: RegistrationService,
    private val appPreferences: AppPreferences
) : AndroidViewModel(application) {
    private val TAG = "RegistrationViewModel"

    val userId: LiveData<Long> = appPreferences.userId

    var verificationId: String? = null
    private var token: ForceResendingToken? = null

    private val authCallback = object : OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
            super.onCodeSent(s, forceResendingToken)
            verificationId = s
            token = forceResendingToken
            needPinInput.value = true
        }

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            //register device in DB
            goToWelcome.value = true
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)
            isGeneralError.value = "Invalid phone number"
        }
    }

    val goToWelcome = MutableLiveData(false)

    val needPinInput = MutableLiveData(false)

    val isGeneralError = MutableLiveData<String>()

    var phoneNumber: String? = null

    fun registerDevice(phone: String) {
        sendVerificationCode(phone)
        phoneNumber = phone
    }

    private fun sendVerificationCode(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            authCallback
        )
    }
}