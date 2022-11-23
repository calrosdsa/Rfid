package com.teclu.mobility2.ui.screens.setting

import com.teclu.mobility2.data.models.entities.Config
import com.teclu.mobility2.domain.util.UiMessage

data class SettingState (
    val settingState:Config? = null,
    val url_servidor:String = "",
    val loading:Boolean = false,
    val message: UiMessage? = null,
)