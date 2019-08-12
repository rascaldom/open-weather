package com.project.openweather.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.openweather.BR
import com.project.openweather.R
import com.project.openweather.common.base.BaseActivity
import com.project.openweather.common.location.LocationServiceHelper
import com.project.openweather.common.permission.PermissionsCheckHelper
import com.project.openweather.common.ui.RecyclerViewListAdapter
import com.project.openweather.databinding.ActivityMainBinding
import com.project.openweather.databinding.AdapterCitiesItemBinding
import com.project.openweather.network.dto.ListElement
import com.project.openweather.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>(), SwipeRefreshLayout.OnRefreshListener, RecyclerViewListAdapter.ListItemClickListener {

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
            initializeService()
            initializeView()
        }
    }

    override fun onResume() {
        super.onResume()
        LocationServiceHelper.startLocationUpdates()
    }

    private fun initializeService() {
        LocationServiceHelper.initialize(this)
    }

    private fun initializeView() {
        initToolbar()
        initAnotherCitiesList()

        viewModel.getCurrentPositionWeather(true, false)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        viewModel.clickCityInfo.observe(this, Observer { id ->
            moveToDetailActivity(id)
        })
    }

    private fun initAnotherCitiesList() {
        binding.layoutSwipeRefresh.setOnRefreshListener(this)
        viewModel.isRequestCompleted.observe(this, Observer {
            binding.layoutSwipeRefresh.isRefreshing = false
        })
        binding.rvAnotherCities.adapter = object : RecyclerViewListAdapter.Adapter<ListElement, AdapterCitiesItemBinding>(
            layoutId = R.layout.adapter_cities_item,
            variableId = BR.weathersDto,
            clickListener = this@MainActivity
        ) {}
    }

    override fun onRefresh() {
        viewModel.getCurrentPositionWeather(false, false)
    }

    override fun onItemClick(view: View, position: Int) {
        moveToDetailActivity((binding.rvAnotherCities.adapter as? RecyclerViewListAdapter.Adapter<ListElement, *>)?.getItem(position)?.id!!)
    }

    private fun moveToDetailActivity(cityId: Long) {
        startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_KEY_CITY_ID, cityId)
        })
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
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
                    initializeService()
                    initializeView()
                } else {
                    finish()
                }

                return
            }
        }
    }
}
