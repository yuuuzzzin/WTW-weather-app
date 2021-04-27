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
import com.example.wtw_weather_app.set.DailySet
import kotlinx.android.synthetic.main.item_daily.view.*

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.ItemViewHolder>() {
    var itemList = ArrayList<DailySet>()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily, parent, false)
        this.context = parent.context
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyAdapter.ItemViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(items: DailySet) {
        itemList.add(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dailyDateText = itemView.dailyDateText
        val dailyImage = itemView.dailyImage
        val dailyMinMaxTempText = itemView.dailyMinMaxTempText

        fun onBind(position: Int) {
            dailyDateText.text = itemList[position].date

            Glide.with(context)
                .load(itemList[position].imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
                .into(dailyImage)

            dailyMinMaxTempText.text = itemList[position].maxTemp + " / " + itemList[position].minTemp
        }

    }

}