package com.pg.mynewsapp.di.module

import android.content.Context
import androidx.room.Room
import com.pg.mynewsapp.data.local.NewsAppDatabase
import com.pg.mynewsapp.di.BASEURL
import com.pg.mynewsapp.di.DatabaseName
import com.pg.mynewsapp.di.NetworkAPIKey
import com.pg.mynewsapp.utils.DefaultDispatcherProvider
import com.pg.mynewsapp.utils.DispatcherProvider
import com.pg.mynewsapp.utils.NetworkHelper
import com.pg.mynewsapp.utils.network.NetworkHelperImpl
import com.pg.mynewsapp.data.api.AuthTokenInterceptor
import com.pg.mynewsapp.data.api.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {


    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BASEURL baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService = Retrofit.Builder().baseUrl(baseUrl).client(
        okHttpClient
    ).addConverterFactory(gsonConverterFactory).build().create(NetworkService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, authTokenInterceptor: AuthTokenInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(authTokenInterceptor)
        .addInterceptor(httpLoggingInterceptor).build()

    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(@NetworkAPIKey apiKey: String): AuthTokenInterceptor =
        AuthTokenInterceptor(apiKey)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideNewsAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): NewsAppDatabase =
        Room.databaseBuilder(
            context, NewsAppDatabase::class.java, databaseName
        ).build()

    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = "news_app_database"

    @Provides
    @Singleton
    fun provideNetworkConnection(@ApplicationContext context: Context): NetworkHelper =
        NetworkHelperImpl(context)

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    @NetworkAPIKey
    fun provideNetWorkAPIKey(): String = "de8a29bd439647a9a87f2ef98a8c7e58"

    @Provides
    @Singleton
    @BASEURL
    fun provideNetWorkBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideTopHeadlinesDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.topHeadlinesDao()

    @Provides
    @Singleton
    fun provideNewsSourceDao(newsAppDatabase: NewsAppDatabase) = newsAppDatabase.newsSourceDao()

}