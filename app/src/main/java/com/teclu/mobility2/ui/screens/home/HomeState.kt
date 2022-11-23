package com.teclu.mobility2.ui.screens.home

import com.teclu.mobility2.domain.util.NetworkStatus
import com.teclu.mobility2.domain.util.UiMessage

data class HomeState(
    val loading:Boolean = false,
    val message:UiMessage? = null,
    val passwordPref:String= "",
    val netWorkConnection:NetworkStatus= NetworkStatus.Unavailable,
    val marcacionCount:Int = 0
)