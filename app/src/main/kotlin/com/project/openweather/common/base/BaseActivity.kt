package com.project.openweather.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.project.openweather.common.ui.LoadingDialog

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract fun getViewModel(): BaseViewModel?
    abstract val layoutId: Int

    protected lateinit var binding: T

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog(this)

        getViewModel()?.apply {
            loadingSubject.observe(this@BaseActivity, Observer {
                if (it == true) showLoading() else dismissLoading()
            })
        }

        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    private fun showLoading() {
        if (isFinishing) return
        loadingDialog.show()
    }

    private fun dismissLoading() {
        if (isFinishing) return
        loadingDialog.dismiss()
    }
}