package com.teclu.mobility2.domain.useCases

import com.teclu.mobility2.data.ApiService
import com.teclu.mobility2.util.Resource
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAccessInicio @Inject constructor(
    private val apiService: ApiService
){
    operator fun invoke () = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getAccessInicio()
            val response2 = apiService.getAccessSettings()
            emit(Resource.Success(response, data2 = response2))
        }catch(e: IOException){
            emit(Resource.Error(e.localizedMessage?:"Error inesperado"))
        }
    }
}