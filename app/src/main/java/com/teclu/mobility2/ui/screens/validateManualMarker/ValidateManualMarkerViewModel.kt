package com.teclu.mobility2.ui.screens.validateManualMarker

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teclu.mobility2.data.models.dao.AccessDao
import com.teclu.mobility2.domain.util.UiMessage
import com.teclu.mobility2.domain.util.UiMessageManager
import com.teclu.mobility2.ui.screens.accesssToConfig.AccessConfigState
import com.teclu.mobility2.util.constants.Params
import com.teclu.mobility2.util.interfaces.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidateManualMarkerViewModel @Inject constructor(
    private val accessDao: AccessDao,
    appPreferences: AppPreferences,
    savedStateHandle: SavedStateHandle
):ViewModel(){
    val typeAccess = savedStateHandle.get<String>(Params.TYPE_ACCESS_PARAM)
    val facilityCodeP = savedStateHandle.get<String>(Params.FACILITY_CODE_P)
    val cardNumberP = savedStateHandle.get<String>(Params.CARD_NUMBER_P)
    private val uiMessageManager  = UiMessageManager()
    private val binaryCode = MutableStateFlow<Int?>(null)
    private val isAuthenticated = MutableStateFlow(false)
    private val accessCredentials = MutableStateFlow<List<Int>>(emptyList())
    val pin = appPreferences.password
    val state:StateFlow<ValidateManualMarkerState> = combine(
        uiMessageManager.message,
        binaryCode,
        isAuthenticated,
        ::ValidateManualMarkerState
    ).stateIn(
        scope =  viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ValidateManualMarkerState.EMPTY
    )

    init{
        viewModelScope.launch {
            accessDao.getAccess().collect{value->
                try {
                    val cards = value.accessSettings.map { it.cardNumber }
                    this@ValidateManualMarkerViewModel.accessCredentials.emit(cards)
                }catch (e:Exception){
//                    uiMessageManager.emitMessage(UiMessage(message = e.localizedMessage?:"Error inesperado"))
                    this@ValidateManualMarkerViewModel.accessCredentials.emit(emptyList())
                }
            }
        }
        viewModelScope.launch {
            binaryCode.collect{
                if (it != null ) {
                    if(it in accessCredentials.value){
                  this@ValidateManualMarkerViewModel.isAuthenticated.emit(true)
                    }else{
                uiMessageManager.emitMessage(UiMessage("Tarjeta no autorizada: $it"))
                    }
                }
                this@ValidateManualMarkerViewModel.binaryCode.emit(null)
            }
        }
    }

    fun getCode(code:String,valueText: MutableState<String>) {
        val cardCode = code.substring(12).dropLast(2)
        viewModelScope.launch {
            if(cardCode.toBigIntegerOrNull() != null){
                this@ValidateManualMarkerViewModel.binaryCode.emit(cardCode.toInt(2))
                valueText.value = ""
            }else{
                delay(1800)
                valueText.value = ""
                uiMessageManager.emitMessage(UiMessage("Lectura incorrecta vuelva a escanear la tarjeta"))
            }
        }
    }


    fun clearMessage(id:Long){
        viewModelScope.launch {
        uiMessageManager.clearMessage(id)
        }
    }
    fun clearAuth(){
        viewModelScope.launch {
            this@ValidateManualMarkerViewModel.isAuthenticated.emit(false)
        }
    }


}