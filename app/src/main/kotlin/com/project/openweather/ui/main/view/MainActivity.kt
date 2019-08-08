package com.project.openweather.ui.main.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.project.openweather.R
import com.project.openweather.common.location.LocationServiceHelper
import com.project.openweather.common.permission.PermissionsCheckHelper
import com.project.openweather.common.ui.BaseActivity
import com.project.openweather.databinding.ActivityMainBinding
import com.project.openweather.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

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

        if (permissionsCheckHelper.checkPermissions()) {
            initialize()
        }

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            viewModel.getCurrentPositionWeather()
        }
    }

    override fun onResume() {
        super.onResume()

        LocationServiceHelper.startLocationUpdates()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.getCurrentPositionWeather()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initialize() {
        LocationServiceHelper.initialize(this)
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
                    println("Permissions granted.")
                    initialize()
                } else {
                    println("Permissions denied.")
                    finish()
                }

                return
            }
        }
    }
}
