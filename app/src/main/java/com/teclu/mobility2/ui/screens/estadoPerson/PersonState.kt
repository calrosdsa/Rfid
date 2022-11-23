package com.teclu.mobility2.ui.screens.estadoPerson

import com.teclu.mobility2.data.dto.mustering.DataX
import com.teclu.mobility2.domain.util.UiMessage


data class PersonState (
    val loading:Boolean = false,
    val uiMessage: UiMessage? = null,
    val zone: List<DataX> = emptyList()
        )