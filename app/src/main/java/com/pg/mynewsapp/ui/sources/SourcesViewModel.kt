package com.pg.mynewsapp.ui.sources

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pg.mynewsapp.data.model.newssources.asEntity
import com.pg.mynewsapp.data.repository.NewsSourceRepository
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
class SourcesViewModel @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository,
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<List<*>>(networkHelper) {


    init {
        if (checkInternetConnection()) fetchNewsSources()
        else fetchNewsSourcesFromDB()
    }

    @OptIn(FlowPreview::class)
    fun fetchNewsSources() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourceRepository.getNewsSources().map { apiSourceList ->
                apiSourceList.map { it.asEntity() }.toList()
            }.flatMapConcat {
                return@flatMapConcat newsSourceRepository.saveNewsSources(it)
            }.flowOn(dispatcherProvider.io).catch { e ->
                println("Exception $e")
                _data.value = Resource.error(e.toString())
            }.collect {
                Log.d("collect data::", it.toString())
                fetchNewsSourcesFromDB()
            }
        }
    }

    private fun fetchNewsSourcesFromDB() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourceRepository.getAllNewsSources().catch { e ->
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