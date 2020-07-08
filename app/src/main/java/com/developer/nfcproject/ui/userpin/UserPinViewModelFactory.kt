package com.developer.nfcproject.ui.userpin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.nfcproject.services.RegistrationService
import com.developer.nfcproject.utils.AppPreferences
import java.lang.Exception
import java.lang.RuntimeException

class UserPinViewModelFactory(
    private val application: Application,
    private val appPreferences: AppPreferences,
    private val registrationService: RegistrationService
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            UserPinViewModel(application, registrationService, appPreferences) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}