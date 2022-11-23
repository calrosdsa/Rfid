package com.teclu.mobility2.ui.screens.ciudades

import com.teclu.mobility2.data.dto.settings.Ciudade
import com.teclu.mobility2.domain.util.UiMessage

data class CiudadState (
    val ciudades:List<Ciudade> = emptyList(),
    val message: UiMessage? = null
        )