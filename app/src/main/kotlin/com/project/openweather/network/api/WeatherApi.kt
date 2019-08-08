package com.project.openweather.network.api

import com.project.openweather.common.apiKey
import com.project.openweather.network.dto.WeatherDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    fun getCurrentPositionData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") key: String = apiKey): Single<WeatherDto>
}