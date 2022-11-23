package com.teclu.mobility2.domain.interactors

import com.teclu.mobility2.data.store.CardHolderStore
import com.teclu.mobility2.domain.util.Interactor
import com.teclu.mobility2.domain.util.fetch
import com.teclu.mobility2.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateCardHolders @Inject constructor(
    private val cardHolderStore: CardHolderStore,
    private val coroutineDispatchers: AppCoroutineDispatchers,
): Interactor<UpdateCardHolders.Params>() {

    override suspend fun doWork(params: Params) {
        withContext(coroutineDispatchers.io){
            cardHolderStore.fetch(Unit,params.forceRefresh)
        }
    }


    data class Params(
        val forceRefresh: Boolean,
    )

}