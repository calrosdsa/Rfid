package com.teclu.mobility2.domain.observers

import com.teclu.mobility2.data.models.entities.Cardholder
import com.teclu.mobility2.data.models.dao.CardholderDao
import com.teclu.mobility2.domain.util.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserverCardHolders @Inject constructor(
    private val cardholderDao: CardholderDao
): SubjectInteractor<ObserverCardHolders.Params, List<Cardholder>>() {

    override fun createObservable(params: Params): Flow<List<Cardholder>> {
       return cardholderDao.getCardholder()
    }

    data class Params(
        val id:Int? = null
    )
}