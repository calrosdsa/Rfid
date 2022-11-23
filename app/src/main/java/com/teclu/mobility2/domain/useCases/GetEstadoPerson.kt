package com.teclu.mobility2.domain.useCases

import android.util.Log
import com.teclu.mobility2.data.ApiService
import com.teclu.mobility2.data.dto.mustering.DataX
import com.teclu.mobility2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEstadoPerson @Inject constructor(
    private val apiService: ApiService
) {
    operator fun invoke(
        idEstado: Int, idCiudad: Int,
        query:String
    ): Flow<Resource<List<DataX>>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getEstadoPerson(idEstado, idCiudad)
            if(query.isBlank()){
            emit(Resource.Success(response.data))
            }else{
                Log.d("DEBUG_D",query)
            val filterData = response.data.filter { it.nombre.lowercase().contains(query.lowercase()) }
                emit(Resource.Success(filterData))

            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Http errror"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IoException"))
        }
    }
}