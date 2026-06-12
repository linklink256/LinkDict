package com.linkdict.core.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object LinkHttpClientFactory {
    fun create(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
}
