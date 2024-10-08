package com.pg.mynewsapp.ui.topheadline

import androidx.lifecycle.viewModelScope
import com.pg.mynewsapp.data.model.topheadlines.asEntity
import com.pg.mynewsapp.data.repository.TopHeadlineRepository
import com.pg.mynewsapp.ui.base.BaseViewModel
import com.pg.mynewsapp.utils.AppConstant
import com.pg.mynewsapp.utils.DispatcherProvider
import com.pg.mynewsapp.utils.NetworkHelper
import com.pg.mynewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {

    init {
        startFetchingNews()
    }

    fun startFetchingNews() {
        if (checkInternetConnection()) {
            fetchNews()
        } else {
            fetchNewsFromDatabase()
        }
    }

    @OptIn(FlowPreview::class)
    private fun fetchNews() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlines(country = AppConstant.COUNTRY).map {
                it.map { articleApi -> articleApi.asEntity(AppConstant.COUNTRY) }.toList()
            }.flatMapConcat {
                return@flatMapConcat topHeadlineRepository.saveTopHeadlinesArticles(
                    it, AppConstant.COUNTRY
                )
            }.flowOn(dispatcherProvider.io).catch { e ->
                println("Exception $e")
                _data.value = Resource.error(e.toString())
            }.collect {
                fetchNewsFromDatabase()
            }
        }
    }

    private fun fetchNewsFromDatabase() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getAllTopHeadlinesArticles(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io).catch { e ->
                    _data.value = Resource.error(e.toString())
                }.collect {
                    if (!checkInternetConnection() && it.isEmpty()) {
                        _data.value = Resource.error("Data Not found.")
                    } else {
                        _data.value = Resource.success(it)
                    }
                }
        }
    }
}