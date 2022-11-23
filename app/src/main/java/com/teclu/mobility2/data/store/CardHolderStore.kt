    package com.teclu.mobility2.data.store

import com.teclu.mobility2.data.ApiService
import com.teclu.mobility2.data.models.entities.Cardholder
import com.teclu.mobility2.data.models.dao.CardholderDao
import com.teclu.mobility2.data.models.dao.ImageDao
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.teclu.mobility2.data.result.mapper.toCardHolderEntity
import com.teclu.mobility2.data.result.mapper.toImageUser
import com.teclu.mobility2.util.interfaces.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

typealias CardHolderStore = Store<Unit, List<Cardholder>>

@InstallIn(SingletonComponent::class)
@Module
object CardHolderModule{
//    @SuppressLint("SuspiciousIndentation")
    @Provides
    @Singleton
    fun provideCardHolderStore (
        apiService: ApiService,
        cardholderDao: CardholderDao,
//        appUtil: AppUtil,
        appPreferences: AppPreferences,
        imageDao: ImageDao,
//        appTasks: AppTasks,
//        @ApplicationContext context:Context
    ):CardHolderStore = StoreBuilder.from(
        fetcher = Fetcher.of {

            apiService.getCardHolders().data
        },
        sourceOfTruth = SourceOfTruth.of(
          reader = {
              cardholderDao.getCardholder().map {entries ->
                  when {
                      entries.isEmpty() -> null
                      // If the request is expired, our data is stale
                      //         lastRequestStore.isRequestExpired(Duration.ofHours(3)) -> null
                      // Otherwise, our data is fresh and valid
                      else -> entries
                  }
              }
                   },
          writer ={_:Unit,entries->
                  cardholderDao.withTransaction {
              runBlocking {
              val entriesForImage = async {
                  entries.map{
                      it.toImageUser().copy(
                          picture = "${appPreferences.urlServidor}/imagenes/${it.picture}"
                      )
              }
              }
             val entriesR = async {
                 entries.map {
                     it.toCardHolderEntity()
                 }
             }
                  imageDao.deleteAllImages()
              cardholderDao.deleteAll()
//                  Log.d("DEBUG_D",entriesForImage.await().toString())
              imageDao.insertAll(entriesForImage.await())
              cardholderDao.insertAll(entriesR.await())
//                  appTasks.
//              entries.map {
//                  cardholderDao.insert(
//                      Cardholder(
//            guid = "guid",
//            firstName = "amsamksmas",
//            lastName = "asmkmaksmkas",
//            ci = "121201",
//            descriptions = "21k2 121",
//            empresa = "smamkams",
//            estado = "smamsas",
//        )
//                  )
//               }
//                      single_entry
//                  picture = appUtil.getImageBitmap(context,it.picture),
//                      fecha = currentDate
                  //              cardholderDao.insertAll(cardResults)
              }
              }
          },
          delete = {
                   cardholderDao.deleteAll()
          },
          deleteAll = cardholderDao::deleteAll
        )
    ).build()
}
