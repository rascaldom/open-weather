package com.project.openweather.ui.main.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.openweather.R
import com.project.openweather.common.base.BaseActivity
import com.project.openweather.databinding.ActivityDetailBinding
import com.project.openweather.ui.main.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private lateinit var viewModel: MainViewModel

    private val viewModelFactory: ViewModelProvider.Factory by inject()

    override fun getViewModel(): MainViewModel {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        return viewModel
    }

    override val layoutId: Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}