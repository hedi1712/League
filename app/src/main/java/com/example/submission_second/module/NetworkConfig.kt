package com.example.submission_second.module

import com.example.submission_second.api.ApiService
import com.example.submission_second.common.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val constant = Constant()

fun getInterceptor(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    var okhttp = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    return okhttp
}

private val retrofit = Retrofit.Builder().baseUrl(constant.baseUrl)
    .client(getInterceptor())
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}