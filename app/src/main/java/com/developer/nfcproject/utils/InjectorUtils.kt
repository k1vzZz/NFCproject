package com.developer.nfcproject.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.developer.nfcproject.db.*
import com.developer.nfcproject.httpclient.NfcHttpClient
import com.developer.nfcproject.httpclient.NfcHttpClientInterface
import com.developer.nfcproject.nfc.NFCManager
import com.developer.nfcproject.services.OperationsService
import com.developer.nfcproject.services.RegistrationService
import com.developer.nfcproject.ui.operations.OperationsViewModelFactory
import com.developer.nfcproject.ui.pin.PinViewModelFactory
import com.developer.nfcproject.ui.registration.RegistrationViewModelFactory
import com.developer.nfcproject.ui.userpin.UserPinViewModelFactory

object InjectorUtils {

    private fun getOperationsDao(context: Context): OperationsDao =
        AppDatabase.getInstance(context.applicationContext).operationsDao()

    private fun getUserDao(context: Context): UserDao =
        AppDatabase.getInstance(context.applicationContext).userDao()

    private fun getTransportDao(context: Context): TransportDao =
        AppDatabase.getInstance(context.applicationContext).transportDao()

    private fun getTransportTypeDao(context: Context): TransportTypeDao =
        AppDatabase.getInstance(context.applicationContext).transportTypeDao()

    fun provideRegistrationModelFactory(context: Context) =
        RegistrationViewModelFactory(
            context.applicationContext as Application,
            getRegistrationService(context),
            AppPreferences.getInstance(context)
        )

    fun providePinModelFactory(context: Context) =
        PinViewModelFactory(
            context.applicationContext as Application,
            AppPreferences.getInstance(context)
        )

    fun provideUserPinModelFactory(context: Context) =
        UserPinViewModelFactory(
            context.applicationContext as Application,
            AppPreferences.getInstance(context),
            getRegistrationService(context)
        )

    fun provideOperationsModelFactory(context: Context) =
        OperationsViewModelFactory(
            context.applicationContext as Application,
            getOperationsService(context)
        )

    @Volatile
    private var nfcHttpClientInstance: NfcHttpClientInterface? = null

    private fun getHttpClient(context: Context) =
        nfcHttpClientInstance ?: synchronized(this) {
            nfcHttpClientInstance ?: NfcHttpClient().also {
                nfcHttpClientInstance = it
            }
        }

    @Volatile
    private var registrationService: RegistrationService? = null

    private fun getRegistrationService(context: Context) =
        registrationService ?: synchronized(this) {
            registrationService ?: RegistrationService(
                "",
                getHttpClient(context),
                getUserDao(context),
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
            ).also { registrationService = it }
        }

    @Volatile
    private var operationsService: OperationsService? = null

    private fun getOperationsService(context: Context) =
        operationsService ?: synchronized(this) {
            operationsService ?: OperationsService(
                getOperationsDao(context),
                getTransportDao(context),
                getTransportTypeDao(context),
                NFCManager.getInstance(),
                getHttpClient(context),
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
            ).also { operationsService = it }
        }
}