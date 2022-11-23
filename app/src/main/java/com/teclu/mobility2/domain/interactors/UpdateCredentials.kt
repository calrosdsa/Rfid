package com.teclu.mobility2.domain.interactors

import com.teclu.mobility2.data.store.CredentialStore
import com.teclu.mobility2.domain.util.Interactor
import com.teclu.mobility2.domain.util.fetch
import com.teclu.mobility2.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateCredentials @Inject constructor(
    private val credentialStore: CredentialStore,
    private val coroutineDispatchers: AppCoroutineDispatchers,
    ) : Interactor<UpdateCredentials.Params>(){

    override suspend fun doWork(params: Params) {
        withContext(coroutineDispatchers.io){
            credentialStore.fetch("",params.forceRefresh)
        }
    }

    data class Params(
        val forceRefresh:Boolean = false
    )
}