package com.developer.nfcproject.utils

import android.content.Context
import android.content.SharedPreferences

const val SHARED_PREF_NAME = "app_prefs"
const val SERVER_URI = "server_uri"
const val SHOW_WARNINGS_KEY = "show_warnings"
const val SHOW_METRICS_KEY = "show_metrics"
const val SHOW_REQUEST_TEXT_QSR_KEY = "show_request_text_qsr"
const val SHOW_DOWNLOAD_SPEED_KEY = "show_download_speed"
const val DEVICE_ID_KEY = "device_id"
const val EMAIL_ADDRESS_KEY = "email_address"


const val USER_ID = "user_id"
const val USER_PIN = "user_pin"
const val USER_PHONE_NUMBER = "user_phone_number"

class AppPreferences private constructor(private val context: Context) {

    val userId =
        SharedPreferenceLongLiveData(
            context.getAppSharedPreferences(),
            USER_ID,
            0
        )

    fun setUserId(value: Long) {
        context.getAppSharedPreferences().putLong(USER_ID, value)
        deviceIdLiveData.updateValueIfHasNoObservers()
    }

    val userPin =
        SharedPreferenceStringLiveData(
            context.getAppSharedPreferences(),
            USER_PIN,
            ""
        )

    fun setUserPin(value: String) {
        context.getAppSharedPreferences().putString(USER_PIN, value)
        deviceIdLiveData.updateValueIfHasNoObservers()
    }

    val phoneNumber =
        SharedPreferenceStringLiveData(
            context.getAppSharedPreferences(),
            USER_PHONE_NUMBER,
            ""
        )

    fun setPhoneNumber(value: String) {
        context.getAppSharedPreferences().putString(USER_PHONE_NUMBER, value)
        deviceIdLiveData.updateValueIfHasNoObservers()
    }



    val serverUriLiveData =
        SharedPreferenceStringLiveData(
            context.getAppSharedPreferences(),
            SERVER_URI,
            ""
        )

    fun setServerUri(value: String) {
        context.getAppSharedPreferences().putString(SERVER_URI, value)
        serverUriLiveData.updateValueIfHasNoObservers()
    }

    val showRequestTextQSRLiveData =
        SharedPreferenceBooleanLiveData(
            context.getAppSharedPreferences(),
            SHOW_REQUEST_TEXT_QSR_KEY,
            false
        )

    fun setShowRequestTextQSR(value: Boolean) {
        context.getAppSharedPreferences().putBoolean(SHOW_REQUEST_TEXT_QSR_KEY, value)
        showRequestTextQSRLiveData.updateValueIfHasNoObservers()
    }

    val showDownloadSpeedLiveData =
        SharedPreferenceBooleanLiveData(
            context.getAppSharedPreferences(),
            SHOW_DOWNLOAD_SPEED_KEY,
            false
        )

    fun setShowDownloadSpeed(value: Boolean) {
        context.getAppSharedPreferences().putBoolean(SHOW_DOWNLOAD_SPEED_KEY, value)
        showDownloadSpeedLiveData.updateValueIfHasNoObservers()
    }

    val deviceIdLiveData =
        SharedPreferenceStringLiveData(
            context.getAppSharedPreferences(),
            DEVICE_ID_KEY,
            ""
        )

    fun setDeviceId(value: String) {
        context.getAppSharedPreferences().putString(DEVICE_ID_KEY, value)
        deviceIdLiveData.updateValueIfHasNoObservers()
    }

    val emailAddressLiveData =
        SharedPreferenceStringLiveData(context.getAppSharedPreferences(), EMAIL_ADDRESS_KEY, "")

    fun setEmailAddress(value: String) {
        context.getAppSharedPreferences().putString(EMAIL_ADDRESS_KEY, value)
        emailAddressLiveData.updateValueIfHasNoObservers()
    }

    private fun Context.getAppSharedPreferences(): SharedPreferences =
        getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    private fun SharedPreferences.putString(key: String, value: String) =
        edit().putString(key, value).apply()

    private fun SharedPreferences.putBoolean(key: String, value: Boolean) =
        edit().putBoolean(key, value).apply()

    private fun SharedPreferences.putInt(key: String, value: Int) =
        edit().putInt(key, value).apply()

    private fun SharedPreferences.putLong(key: String, value: Long) =
        edit().putLong(key, value).apply()

    private fun SharedPreferences.putFloat(key: String, value: Float) =
        edit().putFloat(key, value).apply()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppPreferences? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AppPreferences(context).also { instance = it }
            }
    }
}