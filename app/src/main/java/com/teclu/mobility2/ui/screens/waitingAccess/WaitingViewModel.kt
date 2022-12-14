package com.teclu.mobility2.ui.screens.waitingAccess

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teclu.mobility2.data.models.dao.CardholderDao
import com.teclu.mobility2.data.models.dao.CredentialDao
import com.teclu.mobility2.data.models.dao.ImageDao
import com.teclu.mobility2.data.models.entities.Marcacion
import com.teclu.mobility2.data.models.dao.MarcacionDao
import com.teclu.mobility2.domain.util.UiMessage
import com.teclu.mobility2.domain.util.UiMessageManager
import com.teclu.mobility2.util.Access
import com.teclu.mobility2.util.constants.Params
import com.teclu.mobility2.util.interfaces.AppTasks
import com.teclu.mobility2.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WaitingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cardholderDao: CardholderDao,
    private val credentialDao: CredentialDao,
    private val marcacionDao: MarcacionDao,
    private val imageDao: ImageDao,
    private val appTasks: AppTasks,
    @ApplicationContext private val context: Context,
): ViewModel() {
    private val typeAccess = savedStateHandle.get<String>(Params.TYPE_ACCESS_PARAM)
    private val facilityCodeP = savedStateHandle.get<String>(Params.FACILITY_CODE_P)
    private val cardNumberP = savedStateHandle.get<String>(Params.CARD_NUMBER_P)
    private val uiMessageManager = UiMessageManager()
    private val userChoices = MutableStateFlow<String?>(null)
    private val accessPerson = MutableStateFlow(AccessPerson())
    private val binaryCode = MutableStateFlow<String?>(null)

    val state: StateFlow<AccessState> = combine(
        accessPerson,
        userChoices,
        uiMessageManager.message,
        binaryCode
    ) { accessPerson, userChoice, message, binaryCode ->
        AccessState(
            personAccess = accessPerson,
            userChoice = userChoice,
            message = message,
            binaryCode = binaryCode

        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AccessState()
    )

    init {
        viewModelScope.launch {
            userChoices.emit(typeAccess)
            if (facilityCodeP != null && cardNumberP != null) {
                checkValidation(facilityCodeP.toInt(), cardNumberP.toInt())
            }
        }
        viewModelScope.launch {
            accessPerson
                .collectLatest {
                    delay(5000)
                    accessPerson.emit(AccessPerson())
                }
        }
        viewModelScope.launch {
            binaryCode.collect {
                    if (it != null) {
                        checkValidation(facilityCode = 213, cardNumber = it.toInt(2))
                    }
                this@WaitingViewModel.binaryCode.emit(null)
            }
        }
    }
    fun getCode(code: String,valueText: MutableState<String>) {
        val cardCode = code.substring(12).dropLast(2)
        viewModelScope.launch {
        if(cardCode.toBigIntegerOrNull() != null){
            this@WaitingViewModel.binaryCode.emit(cardCode)
            valueText.value = ""
        }else{
            delay(1800)
            valueText.value = ""
            uiMessageManager.emitMessage(UiMessage("Lectura incorrecta vuelva a escanear la tarjeta"))
        }
        }
    }


    private fun checkValidation(facilityCode: Int, cardNumber: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val credential = credentialDao.getCredentialByNumber(facilityCode, cardNumber)
                val cardHolder = credential?.guidCardHolder?.let { cardholderDao.getCardholderByGuid(it) }
                val imageUser = credential?.guidCardHolder?.let { imageDao.getUserImage(it) }
                if (credential == null) {
                    uiMessageManager.emitMessage(UiMessage("Credencial No registrada "))
                }
                flow<Access> {
                    try {
                        if (credential != null) {
                            if (credential.estado == "Active") {
                                emit(Access.Accepted("Acceso Permitido", "", Color(0xFF00C853)))
                            } else {
                                val detail = when (credential.estado) {
                                    "Expired" -> "Tarjeta Inactiva"
                                    "Stolen" -> "Tarjeta Robada"
                                    "Lost" -> "Tarjeta Perdida"
                                    else -> "Tarjeta Desconocido"
                                }
                                emit(Access.Denied("Acceso Denegado", detail, Color.Red))
                            }
                        }
                    } catch (e: NullPointerException) {
                        emit(Access.Error("Acceso Denegado", "Error Desconocido", Color.DarkGray))
                    }
                }.collect {
                    when (it) {
                        is Access.Accepted -> {
                            this@WaitingViewModel.accessPerson.emit(
                                AccessPerson(
                                    personName = imageUser?.nombre?:"Visita",
                                    cardNumber = cardNumber,
                                    empresa = cardHolder?.empresa,
                                    ci = cardHolder?.ci,
                                    stateBackGround = it.backGround,
                                    accessDetail = it.accessDetail,
                                    accessState = it.accessState,
                                    picture = imageUser?.picture,
                                )
                            )
                            registrarMarcacion(credential?.uniqueId, true, cardHolder?.guid)
                            playSound(true)
                        }
                        is Access.Denied -> {
                            this@WaitingViewModel.accessPerson.emit(
                                AccessPerson(
                                    personName = imageUser?.nombre,
                                    cardNumber = cardNumber,
                                    empresa = cardHolder?.empresa,
                                    ci = cardHolder?.ci,
                                    stateBackGround = it.backGround,
                                    accessDetail = it.accessDetail,
                                    accessState = it.accessState,
                                    picture = imageUser?.picture,
                                )
                            )
                            registrarMarcacion(credential?.uniqueId, false, cardHolder?.guid)
                            playSound(false)
                        }
                        is Access.Error -> {
                            this@WaitingViewModel.accessPerson.emit(
                                AccessPerson(
                                    personName = imageUser?.nombre,
                                    cardNumber = cardNumber,
                                    empresa = cardHolder?.empresa,
                                    ci = cardHolder?.ci,
                                    stateBackGround = it.backGround,
                                    accessDetail = it.accessDetail,
                                    accessState = it.accessState,
                                    picture = imageUser?.picture,
                                )
                            )
                            playSound(false)
                        }
                    }
                }
            }
        }catch(e:IOException){
            viewModelScope.launch{
            uiMessageManager.emitMessage(UiMessage(e.localizedMessage?:"Unexpected"))
            }
        }
    }

    suspend fun registrarMarcacion(uniqueId: String?, acceso: Boolean, guid: String?) {
        val zone: ZoneId = ZoneId.of("GMT-4")
        val localDateTime = LocalDateTime.now()
        val zoneOffSet: ZoneOffset = zone.rules.getOffset(localDateTime)
        val time = localDateTime.atOffset(zoneOffSet).toEpochSecond()
        val date = localDateTime.atOffset(zoneOffSet)
        if (uniqueId != null) {
            var cardcode = uniqueId.split("|")[0]
            cardcode = cardcode.substring(cardcode.length - 8)
            val estado = "pendiente"
//            val estado = "enviado"
            val marcacion = Marcacion(
                //                fecha = time.minus(it*1000),
                fecha = time,
                date = date,
                cardCode = cardcode,
                tipoMarcacion = typeAccess,
                estado = estado,
                acceso = acceso.toString(),
                guidUser = guid,
                //                nombre = accessPerson.value.personName ,
                nroTarjeta = accessPerson.value.cardNumber,
            )
            marcacionDao.insertMarcacion(marcacion)
            appTasks.sendMarcacion()
            //            val rioRequest: WorkRequest = OneTimeWorkRequestBuilder<SendRioWorker>()
            //                .build()
            //            WorkManager.getInstance(context).enqueue(rioRequest)
        }
    }


    private fun playSound(entra: Boolean) {
        try {
            val mp: MediaPlayer = if (entra) {
                MediaPlayer.create(context, R.raw.correct)
            } else {
                MediaPlayer.create(context, R.raw.error)
            }
            mp.start()
        } catch (e: Exception) {
            Log.d("gta AccessActivity - errorSound", "Exception while playing sound:$e")
        }
    }

    fun clearAccessPerson() {
        viewModelScope.launch {
            this@WaitingViewModel.accessPerson.emit(
                AccessPerson()
            )
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }

//    fun CharSequence.indexOf(
//        char: Char,
//        startIndex: Int = 0,
//        ignoreCase: Boolean = false
//    ): Int {
//        return this.length
//    }

}