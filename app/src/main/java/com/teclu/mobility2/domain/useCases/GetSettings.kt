package com.teclu.mobility2.domain.useCases

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.teclu.mobility2.data.ApiService
import com.teclu.mobility2.data.dto.settings.SettingsDto
import com.teclu.mobility2.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSettings @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke() : Flow<Resource<SettingsDto>> = flow<Resource<SettingsDto>> {
            try{
                val telephoneManger =  context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//                Log.d("SETTING_REQUEST","Begin")
                emit(Resource.Loading())
                val response = apiService.getSettings(telephoneManger.imei)
//                val response = apiService.getSettings("353547080165635")
//            Log.d("SETTING_REQUEST","Success ${response")
//                Log.d("SETTING_REQUEST","Success $response")
//                emit(Resource.Success(data = response, message = telephoneManger.imei))
                emit(Resource.Success(data = response, message = telephoneManger.imei))
            }catch(e: HttpException){
//                Log.d("SETTING_REQUEST","Http ${e.localizedMessage}")
                emit(Resource.Error(e.localizedMessage?:"Http errror"))
            }catch(e: IOException){
//                Log.d("SETTING_REQUEST","Io ${e.localizedMessage}")
                emit(Resource.Error(e.localizedMessage?: "IoException"))
            }
    }
}