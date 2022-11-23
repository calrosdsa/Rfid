package com.teclu.mobility2.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.teclu.mobility2.domain.util.AppDateFormatter

val LocalAppDateFormatter = staticCompositionLocalOf<AppDateFormatter> {
    error("DateFormatter not provided")
}

//val LocalTiviTextCreator = staticCompositionLocalOf<TiviTextCreator> {
  //  error("TiviTextCreator not provided")
//}
