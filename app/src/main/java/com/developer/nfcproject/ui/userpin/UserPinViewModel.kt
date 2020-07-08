package com.developer.nfcproject.ui.userpin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.developer.nfcproject.services.RegistrationService
import com.developer.nfcproject.utils.AppPreferences
import kotlinx.coroutines.launch

class UserPinViewModel(
    application: Application,
    private val registrationService: RegistrationService,
    private val appPreferences: AppPreferences
) : AndroidViewModel(application) {

    val userPin: LiveData<String> = appPreferences.userPin

    val phoneNumber: LiveData<String> = appPreferences.phoneNumber

    val goToWelcome = MutableLiveData(false)

    fun saveUserPin(pin: String) {
        viewModelScope.launch {
            if (userPin.value.isNullOrEmpty()){
                appPreferences.setUserPin(pin)
                val id = registrationService.registerUser(phoneNumber.value!!, pin)
                appPreferences.setUserId(id)
            } else {
                if (userPin.value == pin) {
                    goToWelcome.value = true
                }
            }
        }
    }
}