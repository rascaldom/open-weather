package com.project.openweather.ui.main.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.project.openweather.R
import com.project.openweather.common.KELVIN
import com.project.openweather.common.baseImageUrl
import com.project.openweather.common.ui.GlideApp

@BindingAdapter("bindTemperature")
fun setText(view: TextView, temperature: Double) {
    if (temperature > 0) {
        view.text = String.format(view.context.getString(R.string.current_temperature), String.format("%.1f", temperature + KELVIN))
    }
}

@BindingAdapter("bindImage")
fun loadImage(view: ImageView, iconName: LiveData<String>?) {
    if (iconName != null && iconName.value != null) {
        GlideApp.with(view.context).load("$baseImageUrl${iconName.value}.png").into(view)
    }
}