package com.teclu.mobility2.ui.screens.markings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teclu.mobility2.data.models.dao.ImageDao
import com.teclu.mobility2.domain.observers.ObserverPaginatedMarcaciones
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarkingPViewModel @Inject constructor(
    private val imageDao: ImageDao,
    pagingInteractor: ObserverPaginatedMarcaciones
):ViewModel(){

    val pagingState  = pagingInteractor.flow.cachedIn(viewModelScope)

//    val state: StateFlow<>

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        )
    }
}