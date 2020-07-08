package com.developer.nfcproject.ui.operations

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.nfcproject.services.OperationsService
import java.lang.Exception
import java.lang.RuntimeException

class OperationsViewModelFactory(
    private val application: Application,
    private val operationsService: OperationsService
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            OperationsViewModel(application, operationsService) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}