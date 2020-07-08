package com.developer.nfcproject.services

import android.net.ConnectivityManager
import android.net.Network
import com.developer.nfcproject.db.UserDao
import com.developer.nfcproject.httpclient.NfcHttpClientInterface
import com.developer.nfcproject.models.UserModel
import com.developer.nfcproject.utils.USER_ID
import kotlinx.coroutines.delay
import java.lang.Exception

const val HEADER_USER_ID = "user_id"
const val HEADER_REQUEST_ID = "request_id"
const val HEADER_DATA_DESCRIPTORS = "data_descriptors"
const val HEADER_IS_DIALOG = "is_dialog"
const val HEADER_ANSWER_TEXT = "answer_text"

class RegistrationService(
    private val serverUrl: String,
    private val httpClient: NfcHttpClientInterface,
    private val userDao: UserDao,
    connectivityManager: ConnectivityManager
) {

    private var isNetworkAvailable = false

    init {
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isNetworkAvailable = true
            }

            override fun onLost(network: Network) {
                isNetworkAvailable = false
            }
        })
    }

    suspend fun registerUser(phone:String, pin:String): Long =
        try {
            val user = UserModel(null, phone, pin, null)
            delay(500)
            userDao.insertUser(user)
        } catch (e: Exception) {
            -1
        }
}