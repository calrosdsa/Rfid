package com.teclu.mobility2.data.models.mustering

data class MusteringGrafico(
    val cantidadSeguros: Int,
    val cantidadInseguros: Int,
    val total: Int,
    val zonas: List<MusteringZona>
)
