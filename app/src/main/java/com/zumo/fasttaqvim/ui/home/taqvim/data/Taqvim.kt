package com.zumo.fasttaqvim.ui.home.taqvim.data

import android.annotation.SuppressLint
import android.location.Location
import androidx.core.content.ContentProviderCompat.requireContext
import com.azan.astrologicalCalc.SimpleDate
import com.zumo.fasttaqvim.utils.Location.SavedLocation
import com.zumo.fasttaqvim.utils.Time.TimeHelper
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Taqvim {

    @SuppressLint("SimpleDateFormat")
    fun getData(location: Location): ArrayList<TaqvimModel> {

        val taqvimlist = ArrayList<TaqvimModel>()

        val mounth = SimpleDateFormat("M").format(Date())
        val year = SimpleDateFormat("yyyy").format(Date())

        val lastDayOfMounth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        for (i in 1 until lastDayOfMounth+1) {

            val day = location.let {
                TimeHelper.prayerTime(location = it, date = SimpleDate(
                    i,
                    mounth.toInt(),
                    year.toInt())
                )
            }

            taqvimlist.add(
                day
            )
        }

        return taqvimlist
    }

    @SuppressLint("SimpleDateFormat")
    fun getToday(location: Location): TaqvimModel {
        val today = SimpleDateFormat("dd").format(Date())
        val mounth = SimpleDateFormat("M").format(Date())
        val year = SimpleDateFormat("yyyy").format(Date())

        val day = location.let {
            TimeHelper.prayerTime(location = it, date = SimpleDate(
                today.toInt(),
                mounth.toInt(),
                year.toInt())
            )
        }

        return day
    }
}