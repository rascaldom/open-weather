package com.project.openweather.common.ui

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.project.openweather.R
import kotlinx.android.synthetic.main.layout_loading_dialog.*

class LoadingDialog(context: Context) : Dialog(context, R.style.LoadingDialog), LifecycleObserver {

    init {
        if (context is AppCompatActivity) {
            context.lifecycle.addObserver(this)
        }

        setContentView(R.layout.layout_loading_dialog)
        setCancelable(false)
    }

    override fun show() {
        super.show()

        DrawableImageViewTarget(iv_loading_dialog).apply {
            GlideApp.with(context).load(R.raw.loading_image).into(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun close() {
        if (isShowing) {
            dismiss()
        }
    }
}