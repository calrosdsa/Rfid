package com.teclu.mobility2.data.result.mapper

import com.teclu.mobility2.data.dto.cardHolder.Data
import com.teclu.mobility2.data.dto.credentials.CredentialDto
import com.teclu.mobility2.data.models.entities.Cardholder
import com.teclu.mobility2.data.models.entities.Credential
import com.teclu.mobility2.data.models.entities.ImageUser

fun Data.toCardHolderEntity(): Cardholder {
    return Cardholder(
         guid = guid,
//   firstName = firstName,
//    lastName = lastName,
     ci = ci,
    descriptions = descriptions,
    empresa = empresa,
        estado = estado,
    )
}

fun CredentialDto.toCrendentialEntity(): Credential {
    return Credential(
//        var fecha: String?,
     guid = guid,
     guidCardHolder = guidCardHolder,
     cardNumber = cardNumber,
     facilityCode = facilityCode,
     uniqueId = uniqueId,
    estado = estado ,
    )
}

fun Data.toImageUser():ImageUser {
    return ImageUser(
        userGui = guid,
        nombre = "$firstName  $lastName",
//        picture = picture
    )
}