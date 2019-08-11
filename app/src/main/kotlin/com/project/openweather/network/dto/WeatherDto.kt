package com.project.openweather.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable

@Parcelize
data class Clouds (
    val all: Long
) : Parcelable

@Parcelize
data class Coord (
    val lon: String,
    val lat: String
) : Parcelable

@Parcelize
data class Main (
    val temp: Double,
    val pressure: Long,
    val humidity: Long,
    val tempMin: Double,
    val tempMax: Double
) : Parcelable

@Parcelize
data class Sys (
    val type: Long,
    val id: Long,
    val message: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long
) : Parcelable

@Parcelize
data class Weather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable

@Parcelize
data class Wind (
    val speed: Double,
    val deg: Double
) : Parcelable
