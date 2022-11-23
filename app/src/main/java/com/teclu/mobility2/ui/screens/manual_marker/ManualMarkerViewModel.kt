package com.teclu.mobility2.ui.screens.manual_marker

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teclu.mobility2.data.models.entities.Credential
import com.teclu.mobility2.domain.observers.ObserverCardCredential
import com.teclu.mobility2.util.constants.MainDestination
import com.teclu.mobility2.util.constants.Params
import com.teclu.mobility2.util.interfaces.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class   ManualMarkerViewModel @Inject constructor(
    observerCardCredential: ObserverCardCredential,
//    observerCardHolders: ObserverCardHolders,
    savedStateHandle: SavedStateHandle,
    appPreferences: AppPreferences
):ViewModel() {
    val pagingItems = observerCardCredential.flow.cachedIn(viewModelScope)
    val query = MutableStateFlow("")
    private val userChoice = savedStateHandle.get<String>(Params.TYPE_ACCESS_PARAM)
    val isConsulta = savedStateHandle.get<String>(Params.IS_CONSULTA)
    val manualMarking = appPreferences.marcacionManual
//    val state:StateFlow<ManualMarkerState> = combine(
//        query,
//        observerCardCredential.flow.debounce(400) ,
////        observerCardHolders.flow,
//    ){query,cardCredential->
//        ManualMarkerState(
//            query = query,
////            cardholder = cardHolders
//        )
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = ManualMarkerState()
//    )

    init{
        viewModelScope.launch {
            query.debounce(300).collect{
                observerCardCredential(ObserverCardCredential.Params(
                    pagingConfig = PAGING_CONFIG,
                    query = it
                ))
            }

        }
    }

    fun sendManualConfirmation(cardCredential: Credential?, navController: NavController){
        if(manualMarking == "0"){
        if(isConsulta == "0"){
            navController.navigate(MainDestination.CONSULTA_SCREEN + "?${Params.TYPE_ACCESS_PARAM}=${userChoice}" +
                    "&facility_code=${cardCredential?.facilityCode}&card_number=${cardCredential?.cardNumber}"
            ){
//             popUpTo(573383227)
//                popUpTo(-240764362)
                launchSingleTop = true
//             popUpTo(1665011537)
            }
        }else{

            navController.navigate(MainDestination.VALIDATE_MANUAL_SCREEN + "?${Params.TYPE_ACCESS_PARAM}=${userChoice}" +
                    "&facility_code=${cardCredential?.facilityCode}&card_number=${cardCredential?.cardNumber}"
            ){
//             popUpTo(573383227)
//                popUpTo(-240764362)
                launchSingleTop = true
//             popUpTo(1665011537)
            }
        }
        }else{
            if(isConsulta == "0"){
                navController.navigate(MainDestination.CONSULTA_SCREEN + "?${Params.TYPE_ACCESS_PARAM}=${userChoice}" +
                        "&facility_code=${cardCredential?.facilityCode}&card_number=${cardCredential?.cardNumber}"
                ){
//             popUpTo(573383227)
//                popUpTo((-240764362))
                    launchSingleTop = true
//             popUpTo(1665011537)
                }
            }else{
                navController.navigate(MainDestination.WAITING_SCREEN + "?${Params.TYPE_ACCESS_PARAM}=${userChoice}" +
                        "&facility_code=${cardCredential?.facilityCode}&card_number=${cardCredential?.cardNumber}"
                ){
//             popUpTo(573383227)
//                popUpTo((-240764362))
                    launchSingleTop = true
//             popUpTo(1665011537)
                }
            }
        }
    }

    fun search(searchTerm: String) {
        viewModelScope.launch {
//            Log.d("SEARCH_STATE", searchTerm)
            this@ManualMarkerViewModel.query.emit(searchTerm)
        }
    }

    fun clearQuery(){
        viewModelScope.launch {
            this@ManualMarkerViewModel.query.emit("")
        }
    }


    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 10
        )
    }

}