package com.developer.nfcproject.models

import androidx.room.*

@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true) val userId: Long?,
    val phone: String,
    val pin: String,
    @Embedded val profile: ProfileModel?
)

data class ProfileModel(
    val firstName: String?,
    val lastName: String?
)

@Entity
data class TariffModel(
    @PrimaryKey(autoGenerate = true) val tariffId: Long,
    val name: String,
    val activityTime: Int,
    val cost: Int
)

@Entity(primaryKeys = ["userId", "tariffId"])
data class UserTariffCrossRef(
    val userId: Long,
    val tariffId: Long
)

data class UserWithTariffs(
    @Embedded val user: UserModel,
    @Relation(
        parentColumn = "userId",
        entityColumn = "tariffId",
        associateBy = Junction(UserTariffCrossRef::class)
    )
    val tariffs: List<TariffModel>
)

//Enter with get type
@Entity
data class TransportModel(
    @PrimaryKey(autoGenerate = true) val transportId: Long,
    val number: Int,
    val city: String,
    val typeId: Long
)

@Entity
data class TransportTypeModel(
    @PrimaryKey(autoGenerate = true) val transportTypeId: Long,
    val name: String
)

@Entity
data class RouteModel(
    @PrimaryKey(autoGenerate = true) val routeId: Long,
    val routeTransportId: Long,
    val cost: Int
)

data class TransportAndRoute(
    @Embedded val transport: TransportModel,
    @Relation(
        parentColumn = "transportId",
        entityColumn = "routeTransportId"
    )
    val route: RouteModel
)

@Entity
data class StationModel(
    @PrimaryKey(autoGenerate = true) val stationId: Long,
    val name: String,
    val latitude: Double?,
    val longitude: Double?
)

@Entity(primaryKeys = ["routeId", "stationId"])
data class RouteStationCrossRef(
    val routeId: Long,
    val stationId: Long
)

data class RouteWithStations(
    @Embedded val route: RouteModel,
    @Relation(
        parentColumn = "routeId",
        entityColumn = "stationId",
        associateBy = Junction(RouteStationCrossRef::class)
    )
    val stations: List<StationModel>
)

@Entity
data class OperationModel(
    @PrimaryKey(autoGenerate = true) val operationId: Long,
    val date: Long,
    val operationUserId: Long,
    val operationTransportId: Long
)