package com.teclu.mobility2.data.models.dao

import androidx.room.Dao
import androidx.room.Query
import com.teclu.mobility2.data.models.entities.AccessEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AccessDao:DaoEntity<AccessEntity>(){
    @Query("SELECT * FROM access_table")
     abstract fun getAccess(): Flow<AccessEntity>
    @Query("DELETE FROM access_table")
    abstract suspend fun deleteAll()
}