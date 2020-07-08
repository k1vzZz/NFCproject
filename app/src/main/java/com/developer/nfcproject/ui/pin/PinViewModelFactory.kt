package com.developer.nfcproject.ui.pin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.nfcproject.utils.AppPreferences
import java.lang.Exception
import java.lang.RuntimeException

class PinViewModelFactory(
    private val application: Application,
    private val appPreferences: AppPreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            PinViewModel(application, appPreferences) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}