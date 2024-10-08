package com.pg.mynewsapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TopHeadlinesArticle")
data class Article(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "article_id") val articleId: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "url") val url: String = "",
    @ColumnInfo(name = "urlToImage") val imageUrl: String = "",
    @ColumnInfo(name = "country") val country: String = "",
    @ColumnInfo(name = "language") val language: String = "",
    @ColumnInfo(name = "content") val content: String = "",
    @ColumnInfo(name = "publishedAt") val publishedAt: String = "",
    @Embedded val source: Source
)