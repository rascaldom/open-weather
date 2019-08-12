package com.project.openweather.network.api

import com.project.openweather.common.apiKey
import com.project.openweather.common.bboxKR
import com.project.openweather.network.dto.WeatherDto
import com.project.openweather.network.dto.WeathersDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    fun getCurrentWeatherByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") key: String = apiKey): Single<WeatherDto>

    @GET("/data/2.5/weather")
    fun getCurrentWeatherById(
        @Query("id") id: Long,
        @Query("appid") key: String = apiKey): Single<WeatherDto>

    @GET("/data/2.5/box/city")
    fun getCurrentWeatherByRectangleZone(
        @Query("bbox") bbox: String = bboxKR,
        @Query("appid") key: String = apiKey): Single<WeathersDto>
}