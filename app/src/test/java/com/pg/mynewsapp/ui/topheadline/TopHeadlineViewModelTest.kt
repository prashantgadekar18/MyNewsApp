package com.pg.mynewsapp.ui.topheadline

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.pg.mynewsapp.data.local.entity.Article
import com.pg.mynewsapp.data.local.entity.Source
import com.pg.mynewsapp.data.model.topheadlines.APIArticle
import com.pg.mynewsapp.data.model.topheadlines.APISource
import com.pg.mynewsapp.data.model.topheadlines.asEntity
import com.pg.mynewsapp.data.repository.TopHeadlineRepository
import com.pg.mynewsapp.utils.AppConstant
import com.pg.mynewsapp.utils.DispatcherProvider
import com.pg.mynewsapp.utils.Resource
import com.pg.mynewsapp.utils.TestDispatcherProvider
import com.pg.mynewsapp.utils.network.NetworkHelperImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Mock
    private lateinit var networkConnection: NetworkHelperImpl

    private lateinit var topHeadlineViewModel: TopHeadlineViewModel

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun givenServer200_whenFetchNews_thenShouldReturnSuccess() = runTest {
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

        val article = articleApi.asEntity(country)
        val listOfArticle = mutableListOf<Article>()
        listOfArticle.add(article)

        Mockito.doReturn(true).`when`(networkConnection).isNetworkConnected()
        Mockito.doReturn(flowOf(listOfArticleAPI)).`when`(topHeadlineRepository).getTopHeadlines(country)
        Mockito.doReturn(flowOf(listOfArticle)).`when`(topHeadlineRepository)
            .saveTopHeadlinesArticles(listOfArticle, country)

        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)

        Mockito.verify(topHeadlineRepository).getTopHeadlines(country)
        Mockito.verify(topHeadlineRepository).saveTopHeadlinesArticles(listOfArticle, AppConstant.COUNTRY)
    }

    @Test
    fun givenServer200_whenFetchNewsSavingInDB_thenReturnError() = runTest {
        val country = AppConstant.COUNTRY
        val errorMsg = "An error occurred"
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

        val article = articleApi.asEntity(country)
        val listOfArticle = mutableListOf<Article>()
        listOfArticle.add(article)

        Mockito.doReturn(true).`when`(networkConnection).isNetworkConnected()
        Mockito.doReturn(flowOf(listOfArticleAPI)).`when`(topHeadlineRepository).getTopHeadlines(country)
        Mockito.doReturn(flow<List<Article>> { throw RuntimeException(errorMsg) }).`when`(
            topHeadlineRepository
        )
            .saveTopHeadlinesArticles(listOfArticle, country)

        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)

        topHeadlineViewModel.data.test {
            assertEquals(
                Resource.error<List<Article>>(RuntimeException(errorMsg).toString()),
                awaitItem()
            )

        }

        Mockito.verify(topHeadlineRepository).getTopHeadlines(country)
        Mockito.verify(topHeadlineRepository).saveTopHeadlinesArticles(listOfArticle, AppConstant.COUNTRY)
    }

    @Test
    fun givenServerError_whenFetchNews_thenReturnError() = runTest {
        val country = AppConstant.COUNTRY
        Mockito.doReturn(true).`when`(networkConnection).isNetworkConnected()
        Mockito.doReturn(flow<List<APIArticle>> { throw RuntimeException("broken") }).`when`(
            topHeadlineRepository
        ).getTopHeadlines(country)
        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)
        topHeadlineViewModel.data.test {
            assertEquals(
                Resource.error<List<APIArticle>>(RuntimeException("broken").toString()),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
        Mockito.verify(topHeadlineRepository).getTopHeadlines(country)
    }

    @Test
    fun givenNoInternet_whenFetchNewsFromDB_thenReturnSuccess() = runTest {
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
        Mockito.doReturn(false).`when`(networkConnection).isNetworkConnected()
        Mockito.doReturn(flowOf(listOfArticle)).`when`(topHeadlineRepository)
            .getAllTopHeadlinesArticles(country)
        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)
        topHeadlineViewModel.data.test {
            assertEquals(Resource.success(listOfArticle), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        Mockito.verify(topHeadlineRepository).getAllTopHeadlinesArticles(country)

    }


    @Test
    fun givenNoInternet_whenFetchNewsFromDBEmpty_thenReturnError() = runTest {
        val country = AppConstant.COUNTRY
        Mockito.doReturn(false).`when`(networkConnection).isNetworkConnected()
        Mockito.doReturn(flowOf(emptyList<Article>())).`when`(topHeadlineRepository)
            .getAllTopHeadlinesArticles(country)

        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)

        topHeadlineViewModel.data.test {
            assertEquals(Resource.error<List<Article>>("Data Not found."), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        Mockito.verify(topHeadlineRepository).getAllTopHeadlinesArticles(country)
    }

    @After
    fun tearDown() {

    }
}