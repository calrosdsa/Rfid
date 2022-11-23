package com.teclu.mobility2.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teclu.mobility2.data.models.dao.CardholderDao
import com.teclu.mobility2.data.models.dao.CredentialDao
import com.teclu.mobility2.data.models.dao.ImageDao
import com.teclu.mobility2.data.models.dao.MarcacionDao
import com.teclu.mobility2.data.models.entities.Cardholder
import com.teclu.mobility2.data.models.entities.Credential
import com.teclu.mobility2.data.models.entities.ImageUser
import com.teclu.mobility2.domain.interactors.UpdateCardHolders
import com.teclu.mobility2.domain.interactors.UpdateCredentials
import com.teclu.mobility2.domain.useCases.GetConnection
import com.teclu.mobility2.domain.util.*
import com.teclu.mobility2.util.Resource
import com.teclu.mobility2.util.interfaces.AppPreferences
import com.teclu.mobility2.util.interfaces.AppTasks
import com.teclu.mobility2.util.interfaces.AppUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateCardHolders: UpdateCardHolders,
    private val updateCredentials: UpdateCredentials,
    private val marcacionDao: MarcacionDao,
    private val getConnection: GetConnection,
    appPreferences: AppPreferences,
    private val appTasks: AppTasks,
//    private val appUtil: AppUtil,
//    private val cardholderDao: CardholderDao,
//    private val credentialDao: CredentialDao,
//    private val imageDao: ImageDao,
//    @ApplicationContext private val context: Context
):ViewModel(){
    val tableName = appPreferences.tableNameStream
    private val loadingCounter = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()
    private val networkConnection = MutableStateFlow(NetworkStatus.Unavailable)
    private val marcacionCount = MutableStateFlow(0)

    val state:StateFlow<HomeState> = combine(
        loadingCounter.observable,
        uiMessageManager.message,
        networkConnection,
        marcacionCount,
//        observerMarcacionesCount.flow
    ){loading,message,connection,marcacionCount->
        HomeState(
            loading = loading,
            message = message,
            netWorkConnection = connection,
            marcacionCount = marcacionCount
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeState(),
        started = SharingStarted.WhileSubscribed(5000)
    )

    init{
        getMarcarcaionCount()
//        insertToDb()
        viewModelScope.launch {
            do{
//                   Log.d("DEBUG_D","try again")
                checkConnection()
                delay(3000)
            }while(isActive)
        }
    }

//        fun insertToDb() {
//        viewModelScope.launch(Dispatchers.IO) {
//////            val credentials = ca
//////
////            val imageCount = imageDao.getUserImages()
////            Log.d("DEBUG_D",imageCount.size.toString())
////            val credentials = credentialDao.getCredentialList()
////            val cardHolders = cardholderDao.getCardholder2()
//            imageDao.insert(
//                ImageUser(
//                    userGui = "100aba66-4a9d-47fb-93b7-5a28eb06e365",
//                    nombre = "Juan  Soliz ",
//                    picture = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
//                )
//            )
//            imageDao.insert(
//                ImageUser(
//                    userGui = "5",
//                    nombre = "Ernesto Villa",
////                    picture = BitmapFactory.decodeResource(context.resources,R.drawable.profile)
//                    picture = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
//                )
//            )
//            cardholderDao.insert(
//                Cardholder(
//                    guid = "100aba66-4a9d-47fb-93b7-5a28eb06e365",
////            firstName = "Pedro Alberto",
////            lastName = "Soliz Gallardo",
//                    ci = "89543216 SC",
//                    descriptions = "21k2 121sasas",
//                    empresa = "TECLU ",
//                    estado = "Inactive",
//                )
//            )
//            credentialDao.insert(
//                Credential(
////                    id = 1000,
//                    guidCardHolder = "100aba66-4a9d-47fb-93b7-5a28eb06e365",
//                    guid = "cb8ca593-1ea1-4934-897d-15c1e197309b",
//                    cardNumber = 2937,
//                    facilityCode = 213,
//                    uniqueId = "00000000000000000000000003AA16F3|26",
//                    estado = "Inactive"
//                )
//            )
//            cardholderDao.insert(
//                Cardholder(
//                    guid = "5",
////                firstName = "Juan Marcos",
////                lastName = "Medina Fuentes",
//                    ci = "89543216 SC",
//                    descriptions = "21k2 121sasas",
//                    empresa = "TECLU ",
//                    estado = "Active",
//                )
//            )
//            credentialDao.insert(
//                Credential(
////                    id = 1000,
//                    guidCardHolder = "5",
//                    guid = "ad8ca593-1ea1-4934-897d-15c1e197309b",
//                    cardNumber = 1437,
//                    facilityCode = 213,
//                    uniqueId = "00000000000000000000000003AA11F3|40",
//                    estado = "Active"
//                )
//            )
////            Log.d("CARD_HOLDER_RESULTS", "cardHolderDb $cardHolders")
////            Log.d("CREDENTIAL_RESULTS", "cardHolderDb $credentials")
////        }
//        }
//    }


    private fun checkConnection(){
        viewModelScope.launch {
            getConnection().collect{result->
                when(result){
                    is Resource.Success->{
                        this@HomeViewModel.networkConnection.emit(NetworkStatus.Available)
                        if(marcacionCount.value > 0 ) appTasks.sendMarcaciones()
                        getMarcarcaionCount()
                    }
                    is Resource.Error ->{
                        this@HomeViewModel.networkConnection.emit(NetworkStatus.Unavailable)
                    }
                    else -> {}
                }
            }
        }
    }


    fun getMarcarcaionCount(){
//      observerMarcacionesCount(ObserverMarcacionesCount.Params(Unit))
        viewModelScope.launch {
            val count = marcacionDao.getMarcacionCount()
            this@HomeViewModel.marcacionCount.emit(count)
        }
    }



    //    @SuppressLint("NewApi")
    fun updateCardHolders(){
        viewModelScope.launch {
            updateCardHolders(UpdateCardHolders.Params(true)).collectStatus(
                loadingCounter,
                uiMessageManager
            )
            updateCredentials(UpdateCredentials.Params(true)).collectStatus(
                loadingCounter,
                uiMessageManager
            )
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }



}

