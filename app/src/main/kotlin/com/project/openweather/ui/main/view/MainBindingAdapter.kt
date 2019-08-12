package com.project.openweather.ui.main.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.project.openweather.R
import com.project.openweather.common.KELVIN
import com.project.openweather.common.TIME_FORMAT_HHMMSS
import com.project.openweather.common.baseImageUrl
import com.project.openweather.common.ui.RecyclerViewListAdapter
import com.project.openweather.common.ui.GlideApp
import com.project.openweather.network.dto.ListElement
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bindTemperature")
fun setText(view: TextView, temperature: Double) {
    if (temperature > 0) {
        view.text = String.format(view.context.getString(R.string.unit_temperature), String.format("%.1f", temperature + KELVIN))
    }
}

@BindingAdapter("bindImage")
fun loadImage(view: ImageView, iconName: String?) {
    iconName?.run {
        GlideApp.with(view.context).load("$baseImageUrl${this}.png").into(view)
    }
}

@BindingAdapter("bindList")
fun setList(view: RecyclerView, list: LiveData<List<ListElement>>) {
    (view.adapter as? RecyclerViewListAdapter.Adapter<ListElement, *>)?.run {
        setItems(list.value)
        notifyDataSetChanged()
    }
}

@BindingAdapter("bindDate")
fun setDate(view: TextView, value: Long) {
    Date().apply {
        time = value * 1000
        view.text = this.toString()
    }
}

@BindingAdapter("bindTime")
fun setTime(view: TextView, value: Long) {
    Date().apply {
        time = value * 1000
        view.text = SimpleDateFormat(TIME_FORMAT_HHMMSS).format(this)
    }
}