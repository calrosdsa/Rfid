package com.teclu.mobility2.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.teclu.mobility2.data.dto.settings.Ciudade
import com.teclu.mobility2.data.dto.settings.Coordenada

@Entity(tableName = "config")
data class Config(
    @PrimaryKey val id: Int,
    val url_servidor: String?,
    val url_controladora: String?,
    val coordenada: List<Coordenada>,
    val ciudades:List<Ciudade>,
    val interfaz: String?,
    val zonaHoraria: String?,
    val zonaPoligono: String?,
    val riouser: String?,
    val riopass: String?,
    val localePass: String?,
    val imei:String,
)