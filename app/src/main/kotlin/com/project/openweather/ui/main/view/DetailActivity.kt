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

    companion object {
        const val EXTRA_KEY_CITY_ID = "extra_key_city_id"
    }

    private lateinit var viewModel: MainViewModel

    private val viewModelFactory: ViewModelProvider.Factory by inject()

    private var cityId: Long = 0L

    override fun getViewModel(): MainViewModel {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        return viewModel
    }

    override val layoutId: Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        initializeExtraArgument()
        initializeView()
    }

    private fun initializeExtraArgument() {
        cityId = intent.getLongExtra(EXTRA_KEY_CITY_ID, 0L)
    }

    private fun initializeView() {
        viewModel.getSelectedCityWeather(cityId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
    }
}