package com.project.openweather.di

import androidx.lifecycle.ViewModelProvider
import com.project.openweather.common.base.ViewModelProviderFactory
import com.project.openweather.ui.main.model.MainModel
import com.project.openweather.ui.main.model.MainModelImpl
import com.project.openweather.ui.main.viewmodel.MainViewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val mainModules: Module = module {

    factory {
        MainViewModel(get())
    }

    factory {
        MainModelImpl(get()) as MainModel
    }

    factory {
        ViewModelProviderFactory(get() as MainViewModel) as ViewModelProvider.Factory
    }
}