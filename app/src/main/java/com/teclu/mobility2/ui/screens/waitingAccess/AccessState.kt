package com.teclu.mobility2.ui.screens.waitingAccess

import androidx.compose.ui.graphics.Color
import com.teclu.mobility2.domain.util.UiMessage

data class AccessState(
    val personAccess:AccessPerson? = null,
    val userChoice:String? = null,
    val message: UiMessage? = null,
    val binaryCode:String? = null
)


data class AccessPerson(
    val personName:String? = null,
    val cardNumber:Int? = null,
    val empresa:String? = null,
    val ci:String? = null,
    val picture:String? = "",
    val stateBackGround: Color? = null,
    val accessState:String? = null,
    val accessDetail:String? = null,
)
