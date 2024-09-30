package com.pg.mynewsapp.ui.newsListScreen

import androidx.lifecycle.viewModelScope
import com.pg.mynewsapp.data.local.entity.Article
import com.pg.mynewsapp.data.model.topheadlines.asEntity
import com.pg.mynewsapp.data.model.topheadlines.asLanguageEntity
import com.pg.mynewsapp.data.model.topheadlines.asSourceIdEntity
import com.pg.mynewsapp.data.repository.NewsRepository
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
class NewsListViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {


    fun fetchNewsBySources(sources: String) {
        if (checkInternetConnection()) fetchNewsBySourcesByNetwork(sources)
        else fetchNewsBySourcesByDB(sources)
    }

    @OptIn(FlowPreview::class)
    private fun fetchNewsBySourcesByNetwork(sources: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsBySources(sources = sources).map {
                it.map { articleAPI -> articleAPI.asSourceIdEntity(sources) }
            }.flatMapConcat {
                return@flatMapConcat newsRepository.insertNewsBySources(
                    sourceID = sources, articles = it
                )
            }.flowOn(dispatcherProvider.io).catch { e ->
                _data.value = Resource.error(e.toString())
            }.collect {
                fetchNewsBySourcesByDB(sources)
            }
        }
    }

    private fun fetchNewsBySourcesByDB(sources: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsSourceArticleByDB(sources).catch { e ->
                _data.value = Resource.error(e.toString())
            }.flowOn(dispatcherProvider.io).collect {
                if (!checkInternetConnection() && it.isEmpty()) {
                    _data.value = Resource.error("Data Not found.")
                } else {
                    _data.value = Resource.success(it)
                }
            }
        }
    }

}