package com.developer.nfcproject.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developer.nfcproject.models.*

@Dao
interface OperationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOperation(operationModel: OperationModel)

    @Query("SELECT * FROM operationModel;")
    fun getAll(): LiveData<List<OperationModel>>

    @Query("DELETE FROM operationModel;")
    fun deleteOperations()

    @Transaction
    fun deleteAll() {
        deleteOperations()
    }
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userModel: UserModel): Long

    @Query("SELECT * FROM usermodel;")
    fun getAll(): LiveData<List<UserModel>>

    @Query("DELETE FROM usermodel;")
    fun deleteOperations()

    @Transaction
    fun deleteAll() {
        deleteOperations()
    }
}

@Dao
interface TransportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(transportModel: TransportModel): Long

    @Query("SELECT * FROM transportmodel;")
    fun getAll(): LiveData<List<TransportModel>>

    @Query("DELETE FROM transportmodel;")
    fun deleteOperations()

    @Transaction
    fun deleteAll() {
        deleteOperations()
    }
}

@Dao
interface TransportTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(transportTypeModel: TransportTypeModel): Long

    @Query("SELECT * FROM transporttypemodel;")
    fun getAll(): LiveData<List<TransportTypeModel>>

    @Query("DELETE FROM transporttypemodel;")
    fun deleteOperations()

    @Transaction
    fun deleteAll() {
        deleteOperations()
    }
}