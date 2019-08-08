package com.project.openweather.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract fun getViewModel(): BaseViewModel?
    abstract val layoutId: Int

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewModel()

        binding = DataBindingUtil.setContentView(this, layoutId)
    }
}