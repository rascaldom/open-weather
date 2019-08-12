package com.project.openweather.common

import com.project.openweather.di.mainModules
import com.project.openweather.di.networkModules

val appModules = listOf(networkModules, mainModules)

const val baseApiUrl = "https://api.openweathermap.org/"
const val baseImageUrl = "https://openweathermap.org/img/w/"

const val apiKey = "33d719ef2fdd8e8f82040c07a232f869"

const val bboxKR = "${126.117397903},${34.3900458847},${129.468304478},${38.6122429469},${10}"

// location
const val DEFAULT_UPDATE_INTERVAL = 5000
const val DEFAULT_FASTEST_INTERVAL = 2000
const val DEFAULT_LONGITUDE = 126.98502775209444
const val DEFAULT_LATITUDE = 37.56646932590661

// temperature
const val KELVIN = -273.15

// format
const val TIME_FORMAT_HHMMSS = "HH:mm:ss"