package com.teclu.mobility2.ui.screens.validateManualMarker

import com.teclu.mobility2.domain.util.UiMessage

data class ValidateManualMarkerState(
    val message:UiMessage? = null,
    val binaryCode:Int? = null,
    val isAuthenticated:Boolean = false,
//    val accessValue: List<Int> = emptyList()
){
    companion object{
         val EMPTY = ValidateManualMarkerState()
    }
}
