package com.project.openweather.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.openweather.common.base.BaseViewModel
import com.project.openweather.common.location.LocationServiceHelper
import com.project.openweather.common.ui.SingleLiveEvent
import com.project.openweather.network.dto.ListElement
import com.project.openweather.network.dto.WeatherDto
import com.project.openweather.ui.main.model.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainModel: MainModel) : BaseViewModel() {

    private val _currentCityId = MutableLiveData<Long>()
    private val _currentCityTemperature = MutableLiveData<Double>()
    private val _currentCityWeatherIcon = MutableLiveData<String>()
    private val _currentCityName = MutableLiveData<String>()

    private val _citiesWeatherList = MutableLiveData<List<ListElement>>()

    private val _isRequestCompleted = MutableLiveData<Boolean>()

    private val _clickCityInfo = SingleLiveEvent<Long>()

    private val _selectedCityWeather = MutableLiveData<WeatherDto>()

    val currentCityTemperature: LiveData<Double> get() = _currentCityTemperature
    val currentCityWeatherIcon: LiveData<String> get() = _currentCityWeatherIcon
    val currentCityName: LiveData<String> get() = _currentCityName

    val citiesWeatherList: LiveData<List<ListElement>> get() = _citiesWeatherList

    val isRequestCompleted: LiveData<Boolean> get() = _isRequestCompleted

    val clickCityInfo: LiveData<Long> get() = _clickCityInfo

    val selectedCityWeather: LiveData<WeatherDto> get() = _selectedCityWeather

    val showDetailResult = MutableLiveData<Boolean>()

    fun getCurrentPositionWeather(isVisible: Boolean = true, isTerminate: Boolean = true) {
        addDisposable(mainModel.requestWeatherData(LocationServiceHelper.getLastLocation())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { if (isVisible) mutableLoadingSubject.postValue(true) }
            .doAfterTerminate { if (isTerminate) mutableLoadingSubject.postValue(false) }
            .subscribe ({
                _currentCityId.value = it.id
                _currentCityTemperature.value = it.main.temp
                _currentCityWeatherIcon.value = it.weather[0].icon
                _currentCityName.value = it.name
                if (!isTerminate) {
                    getCitiesWeather(isVisible)
                }
            }, {
                it.printStackTrace()
            }))
    }

    fun getSelectedCityWeather(cityId: Long) {
        addDisposable(mainModel.requestWeatherData(cityId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutableLoadingSubject.postValue(true) }
            .doAfterTerminate { mutableLoadingSubject.postValue(false) }
            .subscribe({
                _selectedCityWeather.value = it
                showDetailResult.value = true
            }, {
                it.printStackTrace()
            }))
    }

    fun getCitiesWeather(isVisible: Boolean = true) {
        addDisposable(mainModel.requestCitiesWeatherData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { if (isVisible) mutableLoadingSubject.postValue(false) }
            .subscribe({
                _citiesWeatherList.value = it.list
                _isRequestCompleted.value = true
            }, {
                _isRequestCompleted.value = true
                it.printStackTrace()
            }))
    }

    fun clickCityInfo() {
        _clickCityInfo.value = _currentCityId.value
    }
}