package com.teclu.mobility2.ui.screens.mustering

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teclu.mobility2.data.dto.mustering.MusteringByCiudadDto
import com.teclu.mobility2.domain.useCases.GetMusteringByCiudad
import com.teclu.mobility2.domain.util.ObservableLoadingCounter
import com.teclu.mobility2.domain.util.UiMessage
import com.teclu.mobility2.domain.util.UiMessageManager
import com.teclu.mobility2.domain.util.UiMessageManager2
import com.teclu.mobility2.util.Resource
import com.teclu.mobility2.util.constants.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusteringViewModel @Inject constructor(
    private val getMusteringByCiudad: GetMusteringByCiudad,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val dataResult = MutableStateFlow<DataResultFloat?>(null)
    private val musteringByZone = MutableStateFlow<MusteringByCiudadDto?>(null)
    private val loadingCounter = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager2()
    val ciudadId = savedStateHandle.get<String>(Params.CIUDAD_PARAM)
    val state:StateFlow<MusteringState> = combine(
        dataResult,
        musteringByZone,
        loadingCounter.observable,
        uiMessageManager.message
    ){result,ciudad ,loading,message->
        MusteringState(
            totalCount = result,
            musteringByCiudad  = ciudad,
            loading = loading,
            uiMessage = message
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MusteringState()
    )

    init {
        if(ciudadId != null){
            viewModelScope.launch {
                do{
                    getMusteringByCiudadFoo(ciudadId.toInt())
                    delay(1000)
                }while(isActive)
            }
        }
    }

    private fun getMusteringByCiudadFoo(ciudadId:Int){
        viewModelScope.launch {
            getMusteringByCiudad(ciudadId).collect{result->
                when(result){
                    is Resource.Loading ->{
//                    loadingCounter.addLoader()
                    }
                    is Resource.Success ->{
//                    loadingCounter.removeLoader()
                        val personas = result.data
                        this@MusteringViewModel.musteringByZone.emit(result.data)
                        if(personas?.data?.total!! > 0){
                            val insegurosF = personas.data.cantidadInseguros.times(360).div(personas.data.total)
                            val segurosF= personas.data.cantidadSeguros.times(360).div(personas.data.total)
//                    Log.d("MUSTERING",insegurosF.toString())
//                    Log.d("MUSTERING",segurosF.toString())

                            this@MusteringViewModel.dataResult.emit(
                                DataResultFloat(
                                    seguros = insegurosF.toFloat(),
                                    inseguros = segurosF.toFloat()
                                )
                            )
                        }else{
                            uiMessageManager.emitMessage(UiMessage(
                                message = "No hay datos disponibles"
                            ))
                        }                }
                    is Resource.Error ->{
                        uiMessageManager.emitMessage(UiMessage(result.message?: "Error Inesperado"))
                    }
                }
            }
        }
    }

    fun clearMessage(){
        viewModelScope.launch {
            uiMessageManager.clearMessage()
        }
    }
}
