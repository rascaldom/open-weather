package com.project.openweather.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.openweather.common.base.BaseViewModel
import com.project.openweather.common.location.LocationServiceHelper
import com.project.openweather.ui.main.model.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainModel: MainModel) : BaseViewModel() {

    private val _currentCityTemperature = MutableLiveData<Double>()
    private val _currentCityWeatherIcon = MutableLiveData<String>()
    private val _currentCityName = MutableLiveData<String>()

    val currentCityTemperature: LiveData<Double> get() = _currentCityTemperature
    val currentCityWeatherIcon: LiveData<String> get() = _currentCityWeatherIcon
    val currentCityName: LiveData<String> get() = _currentCityName

    fun getCurrentPositionWeather() {
        addDisposable(mainModel.requestWeatherData(LocationServiceHelper.getLastLocation())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableLoadingSubject.postValue(true) }
            .doAfterTerminate { mutableLoadingSubject.postValue(false) }
            .subscribe ({
                _currentCityTemperature.value = it.main.temp
                _currentCityWeatherIcon.value = it.weather[0].icon
                _currentCityName.value = it.name
            }, {
                it.printStackTrace()
            }))
    }
}