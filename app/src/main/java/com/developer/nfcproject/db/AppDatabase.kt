package com.developer.nfcproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developer.nfcproject.models.*
import com.developer.nfcproject.utils.DATABASE_NAME

@Database(
    entities = [
        UserModel::class,
        TariffModel::class,
        UserTariffCrossRef::class,
        TransportModel::class,
        TransportTypeModel::class,
        RouteModel::class,
        StationModel::class,
        RouteStationCrossRef::class,
        OperationModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun operationsDao(): OperationsDao
    abstract fun userDao(): UserDao
    abstract fun transportDao(): TransportDao
    abstract fun transportTypeDao(): TransportTypeDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}