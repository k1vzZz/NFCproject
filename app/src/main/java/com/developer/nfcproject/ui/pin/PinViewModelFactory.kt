package com.developer.nfcproject.ui.pin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.RuntimeException

class PinViewModelFactory(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            PinViewModel(application) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}