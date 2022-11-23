package com.teclu.mobility2.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "image_user"
)
data class ImageUser(
    @PrimaryKey val userGui:String,
    val picture:String = "",
    val nombre:String = "",
)
