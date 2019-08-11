package com.project.openweather.network.dto

data class WeathersDto (
    val cod: String,
    val calctime: Double,
    val cnt: Long,
    val list: List<ListElement>
)

data class ListElement (
    val id: Long,
    val name: String,
    val coord: Coord,
    val main: MainClass,
    val dt: Long,
    val wind: Wind,
    val rain: Rain? = null,
    val clouds: Clouds,
    val weather: List<Weather>
)

data class MainClass (
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Double,
    val seaLevel: Double? = null,
    val grndLevel: Double? = null,
    val humidity: Long
)

data class Rain (
    val the3H: Double
)

enum class MainEnum {
    Clouds,
    Rain
}
