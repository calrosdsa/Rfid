package com.teclu.mobility2.domain.observers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.teclu.mobility2.data.models.dao.ImageDao
import com.teclu.mobility2.data.result.MarcacionWithImage
import com.teclu.mobility2.domain.util.*
import com.teclu.mobility2.domain.util.pagination.PaginationMarkers
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import javax.inject.Inject

class ObserverPaginatedMarcaciones @Inject constructor(
    private val imageDao: ImageDao,
):PagingInteractor<ObserverPaginatedMarcaciones.Params, MarcacionWithImage>(){

        private val zone: ZoneId = ZoneId.of("GMT-4")
        private val localDateTime = LocalDateTime.now()
        private val zoneOffSet: ZoneOffset = zone.rules.getOffset(localDateTime)
//        private val offsetDateTime: OffsetDateTime = localDateTime.atOffset(zoneOffSet)
        private val offsetDateTime: OffsetDateTime = localDateTime.atOffset(zoneOffSet).plusHours(1)

    override fun createObservable(params: Params): Flow<PagingData<MarcacionWithImage>> {
//        Log.d("DATE_PICKER", "OBSERVER : ${params.date}")
        val startDate = params.date?.minusMinutes(1)?.toEpochSecond()?: offsetDateTime.minusMonths(2).toEpochSecond()
//        val startDate = params.date?.toEpochSecond()?: offsetDateTime.minusMonths(2).toEpochSecond()
//        val endDate = params.date?.toEpochSecond() ?:offsetDateTime.toEpochSecond()
        val endDate = params.date?.plusHours(24)?.toEpochSecond() ?:offsetDateTime.toEpochSecond()
        return  Pager(
            config = params.pagingConfig,
            pagingSourceFactory = {
                PaginationMarkers(
                    imageDao,
                    params.sorted,
//                    params.dayOptions,
                    params.tipoDeMarcacion,
                    params.marcacionEstado,
                    startDate,
                    endDate
                )
            }
        ).flow


    }
    data class Params(
        override val pagingConfig: PagingConfig,
        val sorted:Boolean,
//        val dayOptions: dayOptions,
        val tipoDeMarcacion: TipoDeMarcacion,
        val marcacionEstado: MarcacionEstado,
        val date: OffsetDateTime? = null
    ): Parameters<MarcacionWithImage>
}