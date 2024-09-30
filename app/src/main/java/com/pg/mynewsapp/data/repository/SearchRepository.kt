package com.pg.mynewsapp.data.repository

import com.pg.mynewsapp.data.api.NetworkService
import com.pg.mynewsapp.data.model.topheadlines.APIArticle
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class SearchRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsByQueries(sources: String): Flow<List<APIArticle>> {
        return flow {
            emit(networkService.getNewsByQueries(sources))
        }.map {
            it.articles
        }
    }
}