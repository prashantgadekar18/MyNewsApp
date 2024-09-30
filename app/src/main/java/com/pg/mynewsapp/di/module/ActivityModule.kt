package com.pg.mynewsapp.di.module

import com.pg.mynewsapp.ui.newsListScreen.NewsListAdapter
import com.pg.mynewsapp.ui.sources.NewsSourcesListAdapter
import com.pg.mynewsapp.ui.topheadline.adapter.TopHeadlineAdapter
import com.pg.mynewsapp.ui.topheadline.adapter.TopHeadlineByListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideNewsSourcesListAdapter() = NewsSourcesListAdapter(ArrayList())

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideTopHeadlineByListAdapter() = TopHeadlineByListAdapter(ArrayList())

    @Provides
    fun provideNewsListAdapter() = NewsListAdapter(ArrayList())

}