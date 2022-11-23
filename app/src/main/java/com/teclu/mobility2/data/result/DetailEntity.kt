package com.teclu.mobility2.data.result

import androidx.room.Embedded
import androidx.room.Relation
import com.teclu.mobility2.data.models.entities.Cardholder
import com.teclu.mobility2.data.models.entities.ImageUser

data class DetailEntity(
    @Embedded val cardImage:ImageUser,
    @Relation(
        parentColumn = "userGui",
        entityColumn = "guid"
    )
    val cardHolder:Cardholder
)