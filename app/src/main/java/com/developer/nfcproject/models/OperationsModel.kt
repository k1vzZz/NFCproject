package com.developer.nfcproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

enum class TransportType {
    BUS,
    MINIBUS,
    TRAM
}

data class OperationsModel(
    var id: Long,
    var operationDate: Long = System.currentTimeMillis(),
    var transportType: String = TransportType.BUS.toString(),
    var transportNumber: Int = 0,
    var operation: String = ""
)