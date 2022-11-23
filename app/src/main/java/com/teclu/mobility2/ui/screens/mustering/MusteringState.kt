package com.teclu.mobility2.ui.screens.mustering

import com.teclu.mobility2.data.dto.mustering.MusteringByCiudadDto
import com.teclu.mobility2.domain.util.UiMessage

data class MusteringState(
    val totalCount : DataResultFloat? = null,
    val musteringByCiudad: MusteringByCiudadDto? = null,
    val loading:Boolean = false,
    val uiMessage: UiMessage? = null
)


data class DataResult(
    val total:Int,
    val seguros:Int,
    val inseguros:Int
)

data class DataResultFloat(
    val seguros:Float? = null,
    val inseguros: Float? = null
)