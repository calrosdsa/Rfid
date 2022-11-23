package com.teclu.mobility2.domain.observers

import com.teclu.mobility2.data.models.dao.MarcacionDao
import com.teclu.mobility2.domain.util.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserverMarcacionesCount @Inject constructor(
    private val marcacionDao: MarcacionDao
) : SubjectInteractor<ObserverMarcacionesCount.Params, Int>() {

    override fun createObservable(params: Params): Flow<Int> {
        return marcacionDao.getCountMarcaciones()
    }

    data class Params(
        val unit:Unit
    )
}