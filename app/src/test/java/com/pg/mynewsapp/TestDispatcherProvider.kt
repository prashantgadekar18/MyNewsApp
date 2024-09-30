package com.pg.mynewsapp

import app.cash.turbine.test
import com.pg.mynewsapp.data.api.NetworkService
import com.pg.mynewsapp.data.local.dao.TopHeadlinesDao
import com.pg.mynewsapp.data.local.entity.Article
import com.pg.mynewsapp.data.local.entity.Source
import com.pg.mynewsapp.data.model.topheadlines.APIArticle
import com.pg.mynewsapp.data.model.topheadlines.APISource
import com.pg.mynewsapp.data.model.topheadlines.TopHeadlinesResponse
import com.pg.mynewsapp.data.repository.TopHeadlineRepository
import com.pg.mynewsapp.utils.AppConstant
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class TestDispatcherProvider {

    @Mock
    private lateinit var networkService: NetworkService

    @Mock
    private lateinit var topHeadlinesDao: TopHeadlinesDao

    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Before
    fun setUp() {
        topHeadlineRepository = TopHeadlineRepository(networkService, topHeadlinesDao)
    }

    @Test
    fun whenGetTopHeadlines_thenReturnListOfArticleAPI() = runTest {
        val country = AppConstant.COUNTRY
        val source = APISource(
            id = "sourceId", name = "sourceName"
        )
        val articleApi = APIArticle(
            title = "title",
            description = "description",
            url = "url",
            imageUrl = "urlToImage",
            source = source
        )
        val listOfArticleAPI = mutableListOf<APIArticle>()
        listOfArticleAPI.add(articleApi)

        val topHeadlinesResponse = TopHeadlinesResponse(
            status = "ok", count = 1, articles = listOfArticleAPI
        )
        doReturn(topHeadlinesResponse).`when`(networkService).getTopHeadlines(country)

        topHeadlineRepository.getTopHeadlines(country).test {
            assertEquals(topHeadlinesResponse.articles, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

//        verify(networkService).getTopHeadlines(country)
        verify(networkService, times(1)).getTopHeadlines(country)
    }

    @Test
    fun whenSaveTopHeadlinesArticles_thenReturnListOfInsertedRowId() = runTest {
        val country = AppConstant.COUNTRY
        val source = Source(
            sourceId = "sourceId", name = "sourceName"
        )
        val article = Article(
            title = "title",
            description = "description",
            url = "url",
            imageUrl = "urlToImage",
            source = source
        )
        val listOfArticle = mutableListOf<Article>()
        listOfArticle.add(article)

        val savedArticleRowId = listOf<Long>(1)

        doReturn(savedArticleRowId).`when`(topHeadlinesDao)
            .insertAndDeleteTopHeadlineArticles(country = country, articles = listOfArticle)

        topHeadlineRepository.saveTopHeadlinesArticles(articles = listOfArticle, country = country)
            .test {
                TestCase.assertEquals(savedArticleRowId, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

        verify(topHeadlinesDao).insertAndDeleteTopHeadlineArticles(
            country = country, articles = listOfArticle
        )
    }

    @Test
    fun whenGetAllTopHeadlinesArticlesFromDB_thenShouldReturnArticleEntity() = runTest {
        val country = AppConstant.COUNTRY
        val source = Source(
            sourceId = "sourceId", name = "sourceName"
        )
        val article = Article(
            title = "title",
            description = "description",
            url = "url",
            imageUrl = "urlToImage",
            source = source
        )
        val listOfArticle = mutableListOf<Article>()
        listOfArticle.add(article)

        doReturn(flowOf(listOfArticle)).`when`(topHeadlinesDao).getAllTopHeadlinesArticle(country)
        topHeadlineRepository.getAllTopHeadlinesArticles(country)

        verify(topHeadlinesDao).getAllTopHeadlinesArticle(country)

    }
}