package com.project.openweather

import android.app.Application
import com.project.openweather.common.appModules
import org.koin.android.ext.android.startKoin

class OpenWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, appModules)
    }
}