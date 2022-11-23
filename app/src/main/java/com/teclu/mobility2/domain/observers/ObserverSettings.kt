package com.teclu.mobility2.domain.observers

import com.teclu.mobility2.data.models.dao.ConfigDao
import com.teclu.mobility2.data.models.entities.Config
import com.teclu.mobility2.domain.util.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserverSettings @Inject constructor(
    private val configDao: ConfigDao
) : SubjectInteractor<ObserverSettings.Params,Config>() {

    override fun createObservable(params: Params): Flow<Config> {
        return configDao.getConfigFlow()
    }

    data class Params(
        val unit:Unit
    )
}