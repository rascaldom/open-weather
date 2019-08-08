package com.project.openweather.di

import com.project.openweather.BuildConfig
import com.project.openweather.common.baseApiUrl
import com.project.openweather.network.api.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModules: Module = module {

    single {
        Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor(get() as HttpLoggingInterceptor)
                .build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseApiUrl)
            .build()
            .create(WeatherApi::class.java)
    }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
}