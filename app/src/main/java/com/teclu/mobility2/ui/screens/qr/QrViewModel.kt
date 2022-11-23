package com.teclu.mobility2.ui.screens.qr

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.teclu.mobility2.util.constants.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val qr_result = savedStateHandle.get<String>(Params.QR_PARAM )
}