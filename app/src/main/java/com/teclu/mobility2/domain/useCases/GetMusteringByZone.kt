package com.teclu.mobility2.domain.useCases

import com.teclu.mobility2.data.ApiService
import com.teclu.mobility2.data.dto.mustering.DataX
import com.teclu.mobility2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMusteringByZone @Inject constructor(
    private val apiService: ApiService
) {
    operator fun invoke(zone: Int, ciudad: Int,query:String): Flow<Resource<List<DataX>>> = flow {
        try {
//            Log.d("ZONE_MUS","beggin")

            emit(Resource.Loading())
            val response = apiService.getMusteringByZona(zone, ciudad)
//            Log.d("ZONE_MUS",response.data.toString())
            if(query.isBlank()){
                emit(Resource.Success(response.data))
            }else{
//                Log.d("DEBUG_D",query)
                val filterData = response.data.filter { it.nombre.lowercase().contains(query.lowercase()) }
                emit(Resource.Success(filterData))
            }
//            Log.d("ZONE_MUS",response.toString())
        }catch(e: HttpException){
//                Log.d("SETTING_REQUEST","Http ${e.localizedMessage}")
//            Log.d("ZONE_MUS",e.message.toString())
            emit(Resource.Error(e.localizedMessage?:"Http errror"))
        }catch(e: IOException){
//                Log.d("SETTING_REQUEST","Io ${e.localizedMessage}")
//            Log.d("ZONE_MUS",e.message.toString())
            emit(Resource.Error(e.localizedMessage?: "IoException"))
        }
    }
}
