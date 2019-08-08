package com.project.openweather.ui.main.viewmodel

import com.project.openweather.common.location.LocationServiceHelper
import com.project.openweather.common.base.BaseViewModel
import com.project.openweather.ui.main.model.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainModel: MainModel) : BaseViewModel() {

    fun getCurrentPositionWeather() {
        addDisposable(mainModel.requestWeatherData(LocationServiceHelper.getLastLocation())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableLoadingSubject.postValue(true) }
            .doAfterTerminate { mutableLoadingSubject.postValue(false) }
            .subscribe ({
                println("[rascaldom] success")
            }, {
                println("[rascaldom] fail")
                it.printStackTrace()
            }))
    }
}