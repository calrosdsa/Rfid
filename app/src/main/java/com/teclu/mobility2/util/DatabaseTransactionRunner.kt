package com.teclu.mobility2.util

import androidx.room.withTransaction
import com.teclu.mobility2.data.AppDatabase
import javax.inject.Inject

interface DatabaseTransactionRunner {
    suspend operator fun <T> invoke(block: suspend () -> T): T
}

class RoomTransactionRunner @Inject constructor(
    private val db: AppDatabase
) : DatabaseTransactionRunner {
    override suspend operator fun <T> invoke(block: suspend () -> T): T {
        return db.withTransaction {
            block()
        }
    }
}