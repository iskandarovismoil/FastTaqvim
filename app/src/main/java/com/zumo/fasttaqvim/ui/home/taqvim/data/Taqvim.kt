package com.zumo.fasttaqvim.ui.home.taqvim.data

import android.annotation.SuppressLint
import android.location.Location
import com.azan.astrologicalCalc.SimpleDate
import com.zumo.fasttaqvim.utils.Utils
import com.zumo.fasttaqvim.utils.time.TimeHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Taqvim {

    @SuppressLint("SimpleDateFormat")
    fun getData(location: Location): ArrayList<TaqvimModel> {

        val taqvimList = ArrayList<TaqvimModel>()

        val today = SimpleDateFormat("dd").format(Date())
        val month = SimpleDateFormat("M").format(Date())
        val year = SimpleDateFormat("yyyy").format(Date())

        val lastDayOfMounth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        for (i in 1 until lastDayOfMounth+1) {

            val date = SimpleDate(
                day = i,
                month = month.toInt(),
                year = year.toInt()
            )


            val azanTimes = location.let {
                TimeHelper.prayerTime(location = it, date = date)
            }


            if(today.toInt() == date.day) {
                taqvimList.add(
                    TaqvimModel(
                        bomdod = Utils.timeFormatHourMinute(azanTimes.fajr().toString()),
                        peshin = Utils.timeFormatHourMinute(azanTimes.thuhr().toString()),
                        asr = Utils.timeFormatHourMinute(azanTimes.assr().toString()),
                        shom = Utils.timeFormatHourMinute(azanTimes.maghrib().toString()),
                        hufton = Utils.timeFormatHourMinute(azanTimes.ishaa().toString()),
                        today = true
                    )
                )
            } else {
                taqvimList.add(
                    TaqvimModel(
                        bomdod = Utils.timeFormatHourMinute(azanTimes.fajr().toString()),
                        peshin = Utils.timeFormatHourMinute(azanTimes.thuhr().toString()),
                        asr = Utils.timeFormatHourMinute(azanTimes.assr().toString()),
                        shom = Utils.timeFormatHourMinute(azanTimes.maghrib().toString()),
                        hufton = Utils.timeFormatHourMinute(azanTimes.ishaa().toString()),
                        today = false
                    )
                )
            }
        }

        return taqvimList
    }

    @SuppressLint("SimpleDateFormat")
    fun getToday(location: Location): TaqvimModel {
        val today = SimpleDateFormat("dd").format(Date())
        val mounth = SimpleDateFormat("M").format(Date())
        val year = SimpleDateFormat("yyyy").format(Date())

        val azanTimes = location.let {
            TimeHelper.prayerTime(location = it, date = SimpleDate(
                today.toInt(),
                mounth.toInt(),
                year.toInt())
            )
        }

        return TaqvimModel(
            bomdod = Utils.timeFormatHourMinute(azanTimes.fajr().toString()),
            peshin = Utils.timeFormatHourMinute(azanTimes.thuhr().toString()),
            asr = Utils.timeFormatHourMinute(azanTimes.assr().toString()),
            shom = Utils.timeFormatHourMinute(azanTimes.maghrib().toString()),
            hufton = Utils.timeFormatHourMinute(azanTimes.ishaa().toString()),
            today = true
        )
    }
}