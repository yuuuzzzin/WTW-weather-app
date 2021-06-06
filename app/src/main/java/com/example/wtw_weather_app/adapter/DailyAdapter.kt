package com.example.wtw_weather_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.wtw_weather_app.R
import com.example.wtw_weather_app.model.entity.HourlySet
import kotlinx.android.synthetic.main.item_hourly.view.*

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.ItemViewHolder>() {
    var itemList = ArrayList<HourlySet>()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_hourly, parent, false)
        this.context = parent.context
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyAdapter.ItemViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(items: HourlySet) {
        itemList.add(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hourlyTimeText = itemView.hourlyTimeText
        val hourlyImage = itemView.hourlyImage
        val hourlyTempText = itemView.hourlyTempText

        fun onBind(position: Int) {
            hourlyTimeText.text = itemList[position].time
            Glide.with(context)
                .load(itemList[position].imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL).apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
                .into(hourlyImage)
            hourlyTempText.text = itemList[position].temp
        }

    }
}
