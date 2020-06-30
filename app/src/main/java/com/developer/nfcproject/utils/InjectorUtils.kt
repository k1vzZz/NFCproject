package com.developer.nfcproject.utils

import android.app.Application
import android.content.Context
import com.developer.nfcproject.ui.pin.PinViewModelFactory
import com.developer.nfcproject.ui.registration.RegistrationViewModelFactory

object InjectorUtils {

    fun provideRegistrationModelFactory(context: Context) =
        RegistrationViewModelFactory(
            context.applicationContext as Application
        )

    fun providePinModelFactory(context: Context) =
        PinViewModelFactory(
            context.applicationContext as Application
        )
}