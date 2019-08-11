package com.project.openweather.ui.main.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.project.openweather.R
import com.project.openweather.common.KELVIN
import com.project.openweather.common.baseImageUrl
import com.project.openweather.common.ui.BaseListAdapter
import com.project.openweather.common.ui.GlideApp
import com.project.openweather.network.dto.ListElement
import com.project.openweather.network.dto.WeathersDto

@BindingAdapter("bindTemperature")
fun setText(view: TextView, temperature: Double) {
    if (temperature > 0) {
        view.text = String.format(view.context.getString(R.string.current_temperature), String.format("%.1f", temperature + KELVIN))
    }
}

@BindingAdapter("bindImage")
fun loadImage(view: ImageView, iconName: LiveData<String>?) {
    iconName?.value?.run {
        GlideApp.with(view.context).load("$baseImageUrl${this}.png").into(view)
    }
}

@BindingAdapter("bindList")
fun setList(view: RecyclerView, list: List<ListElement>) {
    (view.adapter as? BaseListAdapter.Adapter<ListElement, *>)?.run {
        setItems(list)
        notifyDataSetChanged()
    }
}