package com.project.openweather.ui.main.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.openweather.BR
import com.project.openweather.R
import com.project.openweather.common.base.BaseActivity
import com.project.openweather.common.location.LocationServiceHelper
import com.project.openweather.common.permission.PermissionsCheckHelper
import com.project.openweather.common.ui.BaseListAdapter
import com.project.openweather.databinding.ActivityMainBinding
import com.project.openweather.databinding.AdapterCitiesItemBinding
import com.project.openweather.network.dto.ListElement
import com.project.openweather.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private val permissionsCheckHelper = PermissionsCheckHelper(this)

    private lateinit var viewModel: MainViewModel

    private val viewModelFactory: ViewModelProvider.Factory by inject()

    override fun getViewModel(): MainViewModel {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        return viewModel
    }

    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        if (permissionsCheckHelper.checkPermissions()) {
            initialize()
        }
    }

    override fun onResume() {
        super.onResume()

        LocationServiceHelper.startLocationUpdates()
    }

    private fun initialize() {
        LocationServiceHelper.initialize(this)
        initToolbar()
        initAnotherCitiesList()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = null
    }

    private fun initAnotherCitiesList() {
        binding.layoutSwipeRefresh.setOnRefreshListener(this)
        viewModel.isRequestCompleted.observe(this, Observer {
            binding.layoutSwipeRefresh.isRefreshing = false
        })
        binding.rvAnotherCities.adapter = object : BaseListAdapter.Adapter<ListElement, AdapterCitiesItemBinding>(
            layoutId = R.layout.adapter_cities_item,
            variableId = BR.weathersDto
        ) {}
    }

    override fun onRefresh() {
        viewModel.getCurrentPositionWeather(false, false)
    }

    override fun onPause() {
        super.onPause()

        LocationServiceHelper.stopLocationUpdates()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionsCheckHelper.PERMISSIONS_REQUEST_CODE -> {
                val isPermissionsGranted = permissionsCheckHelper.processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    initialize()
                } else {
                    finish()
                }

                return
            }
        }
    }
}
