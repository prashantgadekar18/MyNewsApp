package com.pg.mynewsapp.data.local.entity

import androidx.room.ColumnInfo


data class Source(
    @ColumnInfo(name = "sourceId") val sourceId: String = "",
    @ColumnInfo(name = "name") val name: String = ""
)
