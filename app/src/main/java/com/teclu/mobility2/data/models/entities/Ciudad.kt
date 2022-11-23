package com.teclu.mobility2.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ciudad")
data class Ciudad(
    @PrimaryKey val id: Int,
    val nombre: String,
)
