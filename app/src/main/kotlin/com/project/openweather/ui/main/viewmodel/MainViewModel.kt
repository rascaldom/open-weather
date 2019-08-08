package com.project.openweather.ui.main.viewmodel

import com.project.openweather.common.DEFAULT_LATITUDE
import com.project.openweather.common.DEFAULT_LONGITUDE
import com.project.openweather.common.ui.BaseViewModel
import com.project.openweather.ui.main.model.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainModel: MainModel) : BaseViewModel() {

    fun getCurrentPositionWeather() {
        addDisposable(mainModel.requestWeatherData(DEFAULT_LATITUDE.toString(), DEFAULT_LONGITUDE.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                println("[rascaldom] success")
            }, {
                println("[rascaldom] fail")
                it.printStackTrace()
            }))
    }
}