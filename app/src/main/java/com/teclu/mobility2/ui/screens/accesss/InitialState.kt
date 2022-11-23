package com.teclu.mobility2.ui.screens.accesss

import com.teclu.mobility2.domain.util.UiMessage

data class InitialState(
    val message:UiMessage? = null,
    val binaryCode:Int? = null,
    val isAuthenticated:Boolean = false,
    val accessValue: List<Int> = emptyList()
)
