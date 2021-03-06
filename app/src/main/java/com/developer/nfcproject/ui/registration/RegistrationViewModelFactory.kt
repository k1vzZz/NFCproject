package com.developer.nfcproject.ui.registration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.nfcproject.services.RegistrationService
import com.developer.nfcproject.utils.AppPreferences
import java.lang.Exception
import java.lang.RuntimeException

class RegistrationViewModelFactory(
    private val application: Application,
    private val registrationService: RegistrationService,
    private val appPreferences: AppPreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            RegistrationViewModel(application, registrationService, appPreferences) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}