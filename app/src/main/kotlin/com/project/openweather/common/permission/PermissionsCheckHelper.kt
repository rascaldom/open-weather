package com.project.openweather.common.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity

class PermissionsCheckHelper(
    private val activity: AppCompatActivity,
    private val list: List<String> = permissionsList,
    private val code:Int = PERMISSIONS_REQUEST_CODE) {

    companion object {
        const val PERMISSIONS_REQUEST_CODE = 1000

        val permissionsList = listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    fun checkPermissions(): Boolean {
        return if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
            false
        } else {
            true
        }
    }

    private fun isPermissionsGranted(): Int {
        var counter = 0
        for (permission in list) {
            counter += ContextCompat.checkSelfPermission(activity, permission)
        }

        return counter
    }

    private fun deniedPermission(): String {
        for (permission in list) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
                return permission
            }
        }

        return ""
    }

    private fun requestPermissions() {
        val permission = deniedPermission()
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            println("shouldShowRequestPermissionRationale() true")
        } else {
            println("shouldShowRequestPermissionRationale() false")
        }
        ActivityCompat.requestPermissions(activity, list.toTypedArray(), code)
    }

    fun processPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
        var result = 0
        if (grantResults.isNotEmpty()) {
            for (item in grantResults) {
                result += item
            }
        }

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true
        }

        return false
    }
}