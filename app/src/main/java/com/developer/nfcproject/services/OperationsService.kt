package com.developer.nfcproject.services

import android.net.ConnectivityManager
import android.net.Network
import android.nfc.Tag
import com.developer.nfcproject.ServiceResult
import com.developer.nfcproject.ServiceSuccessResult
import com.developer.nfcproject.db.OperationsDao
import com.developer.nfcproject.db.TransportDao
import com.developer.nfcproject.db.TransportTypeDao
import com.developer.nfcproject.httpclient.NfcHttpClient
import com.developer.nfcproject.httpclient.NfcHttpClientInterface
import com.developer.nfcproject.models.*
import com.developer.nfcproject.nfc.NFCManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OperationsService(
    private val operationsDao: OperationsDao,
    private val transportModel: TransportDao,
    private val transportTypeModel: TransportTypeDao,
    private val nfcManager: NFCManager,
    private val httpClient: NfcHttpClientInterface,
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

    fun getOperationsCache() = operationsDao.getAll()

    fun getOperationsTest(): List<OperationsModel> {
        return listOf(
            OperationsModel(
                id = 1,
                transportType = TransportType.MINIBUS.toString(),
                transportNumber = 34,
                operation = "1 route"
            ),
            OperationsModel(
                id = 2,
                transportType = TransportType.BUS.toString(),
                transportNumber = 72,
                operation = "1 route"
            )
        )
    }

    suspend fun processPay(nfcModel: NfcModel): ServiceResult<OperationsModel> =
        withContext(Dispatchers.IO)
        {
            val transportType1 = TransportTypeModel(0, "BUS")
            val transportType2 = TransportTypeModel(1, "MINUBUS")
            val transportType3 = TransportTypeModel(2, "TRAM")

            transportTypeModel.insertUser(transportType1)
            transportTypeModel.insertUser(transportType2)
            transportTypeModel.insertUser(transportType3)


            val transport = TransportModel(0, nfcModel.messages?.transportNumber?.toInt() ?: 0, "NN", 0)

            val operationsModel = OperationsModel(
                id = 0,
                operationDate = System.currentTimeMillis(),
                transportNumber = 62,
                transportType = TransportType.TRAM.toString(),
                operation = "1 route"
            )
            val nfcDto = NfcDto(TransportType.MINIBUS.toString(), "34")

            //nfcManager.writeData(tag, nfcDto)

            //operationsDao.insertOperation(operationsModel)
            return@withContext ServiceSuccessResult(
                operationsModel
            )
        }

    private fun saveResponse() {

    }
}