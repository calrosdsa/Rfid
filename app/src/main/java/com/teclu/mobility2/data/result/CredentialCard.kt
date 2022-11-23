package com.teclu.mobility2.data.result

import androidx.room.Embedded
import androidx.room.Relation
import com.teclu.mobility2.data.models.entities.Credential
import com.teclu.mobility2.data.models.entities.ImageUser
import java.util.*

class CredentialCard {
    @Embedded
    var credential: Credential? = null

    @Relation(
        parentColumn = "guidCardHolder",
        entityColumn = "userGui"
    )
    var cardImage: ImageUser? = null

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is CredentialCard -> credential == other.credential && cardImage == other.cardImage
        else -> false
    }

    override fun hashCode(): Int = Objects.hash(credential, cardImage)

}
