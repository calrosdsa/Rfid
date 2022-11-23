package com.teclu.mobility2.domain.useCases

import android.util.Log
import com.teclu.mobility2.data.ApiService
import com.teclu.mobility2.util.Resource
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetConnection @Inject constructor(
    private val apiService: ApiService
){
    operator fun invoke () = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getAccessInicio()
            emit(Resource.Success(response))
        }catch(e: IOException){
//            Log.d("DEBUG_D",e.localizedMessage?:"error")
            emit(Resource.Error(e.localizedMessage?:"Error inesperado"))
        }
    }
}