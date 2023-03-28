package com.zumo.fasttaqvim.ui.home.taqvim.data

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.zumo.fasttaqvim.R
import com.zumo.fasttaqvim.utils.Utils.getDateBeauty
import kotlin.collections.ArrayList


class TaqvimAdapter(private var taqvimList: ArrayList<TaqvimModel>, val click: TaqvimInterface) : RecyclerView.Adapter<TaqvimAdapter.Pager2ViewHolder>() {

    var todayPosition: Int? = -1

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item : MaterialCardView = itemView.findViewById(R.id.itemTaqvim)

        val item_closeMouthTime : TextView = itemView.findViewById(R.id.closeMouthTime)

        val item_openMouthTime : TextView = itemView.findViewById(R.id.openMouthTime)

        val item_closeMouthTitle : TextView = itemView.findViewById(R.id.closeMouthTitle)

        val item_openMouthTitle : TextView = itemView.findViewById(R.id.openMouthTitle)

        val item_cardBody : LinearLayout = itemView.findViewById(R.id.cardBody)

        val item_mounthDay : TextView = itemView.findViewById(R.id.mounthDay)

        val item_devider : View = itemView.findViewById(R.id.devider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_taqvim, parent, false))
    }

    override fun getItemCount(): Int {
        return taqvimList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {

        val animation: Animation = AnimationUtils.loadAnimation(holder.item.context, R.anim.slide_in)

        holder.item.startAnimation(animation)

        holder.item_closeMouthTime.text = taqvimList[position].bomdod

        holder.item_mounthDay.text = (position + 1).toString()

        holder.item_openMouthTime.text = taqvimList[position].shom

        if(taqvimList[position].today) {
            holder.item_cardBody.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#CBF94144"))
            holder.item_closeMouthTime.setTextColor(Color.WHITE)
            holder.item_openMouthTime.setTextColor(Color.WHITE)
            holder.item_closeMouthTitle.setTextColor(Color.WHITE)
            holder.item_openMouthTitle.setTextColor(Color.WHITE)
            holder.item_mounthDay.setTextColor(Color.WHITE)
            holder.item_devider.setBackgroundColor(Color.WHITE)

            todayPosition = position
        }else{
            holder.item_cardBody.backgroundTintList = ColorStateList.valueOf(Color.WHITE)

            if(todayPosition == -1 || position < todayPosition!!) {
                holder.item_closeMouthTime.setTextColor(Color.parseColor("#e3e3e3"))
                holder.item_openMouthTime.setTextColor(Color.parseColor("#e3e3e3"))
                holder.item_closeMouthTitle.setTextColor(Color.parseColor("#e3e3e3"))
                holder.item_openMouthTitle.setTextColor(Color.parseColor("#e3e3e3"))
                holder.item_mounthDay.setTextColor(Color.parseColor("#e3e3e3"))
                holder.item_devider.setBackgroundColor(Color.parseColor("#e3e3e3"))
            } else {
                holder.item_closeMouthTime.setTextColor(Color.parseColor("#90be6d"))
                holder.item_openMouthTime.setTextColor(Color.parseColor("#90be6d"))
                holder.item_closeMouthTitle.setTextColor(Color.parseColor("#577590"))
                holder.item_openMouthTitle.setTextColor(Color.parseColor("#577590"))
                holder.item_mounthDay.setTextColor(Color.parseColor("#f3722c"))
                holder.item_devider.setBackgroundColor(Color.parseColor("#f3722c"))
            }
        }

        holder.item.setOnClickListener {

            val date = getDateBeauty((position + 1).toString())

            click.onClick(
                date,
                taqvimList[position].bomdod,
                taqvimList[position].peshin,
                taqvimList[position].asr,
                taqvimList[position].shom,
                taqvimList[position].hufton,
                taqvimList[position].today,
            )
        }
    }

}