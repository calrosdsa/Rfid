package com.teclu.mobility2.ui.screens.accesssToConfig

import com.teclu.mobility2.domain.util.UiMessage

data class AccessConfigState(
    val message:UiMessage? = null,
    val binaryCode:Int? = null,
    val isAuthenticated:Boolean = false,
    val accessValue: List<Int> = emptyList()
)
