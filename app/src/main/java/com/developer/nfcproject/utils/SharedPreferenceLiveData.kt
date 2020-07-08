package com.developer.nfcproject.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferenceLiveData<T> constructor(
    protected val sharedPrefs: SharedPreferences,
    private val key: String,
    private val defaultValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (this.key == key) {
                value = getValueFromPreferences(this.key, defaultValue)
            }
        }

    abstract fun getValueFromPreferences(key: String, defaultValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defaultValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }

    fun updateValueIfHasNoObservers() {
        if (!hasActiveObservers()) {
            value = getValueFromPreferences(key, defaultValue)
        }
    }

    fun isDefaultValue() =
        value == defaultValue
}

class SharedPreferenceBooleanLiveData constructor(
    sharedPrefs: SharedPreferences,
    key: String,
    defaultValue: Boolean
) : SharedPreferenceLiveData<Boolean>(sharedPrefs, key, defaultValue) {

    init {
        value = getValueFromPreferences(key, defaultValue)
    }

    override fun getValueFromPreferences(key: String, defaultValue: Boolean) =
        sharedPrefs.getBoolean(key, defaultValue)
}

class SharedPreferenceIntLiveData constructor(
    sharedPrefs: SharedPreferences,
    key: String,
    defaultValue: Int
) : SharedPreferenceLiveData<Int>(sharedPrefs, key, defaultValue) {

    init {
        value = getValueFromPreferences(key, defaultValue)
    }

    override fun getValueFromPreferences(key: String, defaultValue: Int) =
        sharedPrefs.getInt(key, defaultValue)
}

class SharedPreferenceLongLiveData constructor(
    sharedPrefs: SharedPreferences,
    key: String,
    defaultValue: Long
) : SharedPreferenceLiveData<Long>(sharedPrefs, key, defaultValue) {

    init {
        value = getValueFromPreferences(key, defaultValue)
    }

    override fun getValueFromPreferences(key: String, defaultValue: Long) =
        sharedPrefs.getLong(key, defaultValue)
}

class SharedPreferenceStringLiveData constructor(
    sharedPrefs: SharedPreferences,
    key: String,
    defaultValue: String
) : SharedPreferenceLiveData<String>(sharedPrefs, key, defaultValue) {

    init {
        value = getValueFromPreferences(key, defaultValue)
    }

    override fun getValueFromPreferences(key: String, defaultValue: String) =
        sharedPrefs.getString(key, defaultValue)!!
}

class SharedPreferenceFloatLiveData constructor(
    sharedPrefs: SharedPreferences,
    key: String,
    defaultValue: Float
) : SharedPreferenceLiveData<Float>(sharedPrefs, key, defaultValue) {

    init {
        value = getValueFromPreferences(key, defaultValue)
    }

    override fun getValueFromPreferences(key: String, defaultValue: Float) =
        sharedPrefs.getFloat(key, defaultValue)
}
