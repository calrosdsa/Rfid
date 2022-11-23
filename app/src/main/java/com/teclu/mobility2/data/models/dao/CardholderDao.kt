package com.teclu.mobility2.data.models.dao

import androidx.room.*
import com.teclu.mobility2.data.models.entities.Cardholder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CardholderDao: DaoEntity<Cardholder>(){

    @Transaction
    override suspend fun withTransaction(tx: suspend () -> Unit) = tx()

    @Query("SELECT * FROM cardholder")
    abstract fun getCardholder(): Flow<List<Cardholder>>

    @Query("SELECT * FROM cardholder")
    abstract suspend fun getCardholder2(): List<Cardholder>

    suspend fun insertCardholders(Cardholders: List<Cardholder>){
        deleteAll()
        insertCardholders2(Cardholders)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   abstract suspend fun insertCardholders2(Cardholders: List<Cardholder>)

    @Query("DELETE FROM cardholder")
    abstract fun deleteAll()


    @Query("SELECT * FROM cardholder WHERE guid = :g")
    abstract fun getCardholderByGuid(g: String): Cardholder?

    @Query("DELETE FROM cardholder WHERE guid = :g")
    abstract fun deleteById(g: String)

}