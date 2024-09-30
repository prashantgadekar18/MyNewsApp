package com.pg.mynewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pg.mynewsapp.data.local.dao.NewsSourceDao
import com.pg.mynewsapp.data.local.dao.TopHeadlinesDao
import com.pg.mynewsapp.data.local.entity.Article
import com.pg.mynewsapp.data.local.entity.NewsSource

@Database(
    entities = [Article::class, NewsSource::class],
    version = 1,
    exportSchema = false,
)
abstract class NewsAppDatabase : RoomDatabase() {

    abstract fun topHeadlinesDao(): TopHeadlinesDao

    abstract fun newsSourceDao(): NewsSourceDao

}