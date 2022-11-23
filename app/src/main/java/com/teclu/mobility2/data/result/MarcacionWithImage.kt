package com.teclu.mobility2.data.result

import androidx.room.Embedded
import androidx.room.Relation
import com.teclu.mobility2.data.models.entities.ImageUser
import com.teclu.mobility2.data.models.entities.Marcacion
import java.util.*

 class MarcacionWithImage {
    @Embedded
    var marcacion: Marcacion? = null
    @Relation(
        parentColumn = "guidUser",
        entityColumn = "userGui"
    )
    var cardImageUser: ImageUser? = null


    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is MarcacionWithImage -> marcacion == other.marcacion && cardImageUser == other.cardImageUser
        else -> false
    }

    override fun hashCode(): Int = Objects.hash(marcacion, cardImageUser)

}