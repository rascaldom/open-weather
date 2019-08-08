package com.project.openweather.common.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity

class PermissionsCheckHelper(
        val activity: AppCompatActivity,
        val list: List<String> = permissionsList,
        val code:Int = PERMISSIONS_REQUEST_CODE) {

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
            println("Permissions already granted.")
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
            // 사용자가 다시 보지 않기에 체크를 하지 않고, 권한 설정을 거절한 이력이 있는 경우
            println("shouldShowRequestPermissionRationale() true")
        } else {
            // 사용자가 다시 보지 않기에 체크하고, 권한 설정을 거절한 이력이 있는 경우
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