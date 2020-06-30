package com.developer.nfcproject.ui.registration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.RuntimeException

class RegistrationViewModelFactory(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            RegistrationViewModel(application) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}