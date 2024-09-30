package com.pg.mynewsapp.utils

import androidx.annotation.VisibleForTesting
import com.pg.mynewsapp.utils.network.NetworkError

interface NetworkHelper {

    fun isNetworkConnected(): Boolean

    fun castToNetworkError(throwable: Throwable): NetworkError

    @VisibleForTesting
    fun setStatus(status: Boolean)

}