package com.teclu.mobility2.util.interfaces

import com.teclu.mobility2.data.models.dao.*

interface TecluDatabaseDao {
     fun configDao(): ConfigDao
     fun cardholderDao(): CardholderDao
     fun credentialDao(): CredentialDao
     fun marcacionDao(): MarcacionDao
     fun ciudadDao(): CiudadDao
     fun imageDao():ImageDao
     fun accessDao():AccessDao
     fun userFts4():UserCardFtsDao
}