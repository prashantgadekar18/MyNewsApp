package com.pg.mynewsapp.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pg.mynewsapp.data.model.topheadlines.APIArticle
import com.pg.mynewsapp.data.repository.SearchRepository
import com.pg.mynewsapp.ui.base.BaseViewModel
import com.pg.mynewsapp.utils.DispatcherProvider
import com.pg.mynewsapp.utils.NetworkHelper
import com.pg.mynewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {


    @OptIn(FlowPreview::class)
    fun setUpSearchStateFlow(searchFlow: StateFlow<String>) {
        viewModelScope.launch(dispatcherProvider.main) {
            searchFlow.debounce(2000).filter { query ->
                if (query.isEmpty()) {
                    _data.value = Resource.success(listOf<APIArticle>())
                    return@filter false
                } else {
                    return@filter true
                }
            }.distinctUntilChanged().flatMapLatest { query ->
                Log.d("query", query)
                searchRepository.getNewsByQueries(sources = query).catch { e ->
                    _data.value = Resource.error(e.toString())
                }
            }.flowOn(dispatcherProvider.io).collect {
                _data.value = Resource.success(it)
            }
        }
    }

    fun fetchNewsByQueries(queries: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            searchRepository.getNewsByQueries(sources = queries).catch { e ->
                _data.value = Resource.error(e.toString())
            }.flowOn(dispatcherProvider.io).collect {
                _data.value = Resource.success(it)
            }
        }
    }

}