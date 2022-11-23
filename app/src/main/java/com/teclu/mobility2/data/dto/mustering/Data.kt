package com.teclu.mobility2.data.dto.mustering

data class Data(
    val cantidadInseguros: Int,
    val cantidadSeguros: Int,
    val total: Int,
    val zonas: List<Zona>
)