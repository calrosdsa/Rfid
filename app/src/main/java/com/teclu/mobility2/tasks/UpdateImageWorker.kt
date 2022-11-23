package com.teclu.mobility2.tasks

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.executeBlocking
import coil.request.ImageRequest
import com.teclu.mobility2.data.models.dao.ImageDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateImageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
//    private val updateImages: UpdateImages,
    private val imageDao: ImageDao,
    private val imageLoader: ImageLoader
//    private val apiService: ApiService,
//    private val appPreferences: AppPreferences,
//    private val updateImages: UpdateImages,
//    private val appUtil: AppUtil,
//    private val dispatchers: AppCoroutineDispatchers
) : CoroutineWorker(context,params) {

    companion object {
        const val TAG = "sync-update-images"
//        private const val PARAM_SHOW_ID = "show-id"
//        fun buildData(images: List<ImageEntity>) = Data.Builder()
//            .putAll(images)
//            .build()
    }

    override suspend fun doWork(): Result {
        try {
//            Log.d("DEBUG_D","BEGIN")
            val images = imageDao.getUserImages()
//            Log.d("DEBUG_D",images.size.toString())
            images.filter{it.nombre.isNotBlank()}.map {
                Log.d("DEBUG_D",it.nombre)
                val request = ImageRequest.Builder(applicationContext)
//                    .memoryCacheKey(it.userGui)
                    .data(it.picture)
                    .build()
                imageLoader.executeBlocking(request)
            }
            return Result.success()
        }catch(e:Exception){
//            Log.d("DEBUG_DD", e.localizedMessage?:"Failed Fatal")
            return Result.failure()
        }
    }

}