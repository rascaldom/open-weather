package com.project.openweather.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class WeatherDto (
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)

data class Clouds (
    val all: Long
)

data class Coord (
    val lon: String,
    val lat: String
)

data class Main (
    val temp: Double,
    val pressure: Double,
    val humidity: Long,
    val tempMin: Double,
    val tempMax: Double
)

data class Sys (
    val type: Long,
    val id: Long,
    val message: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Weather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind (
    val speed: Double,
    val deg: Double
)
