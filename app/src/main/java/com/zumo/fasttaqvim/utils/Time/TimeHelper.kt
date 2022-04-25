package com.zumo.fasttaqvim.utils.Time

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.azan.Azan
import com.azan.Madhhab
import com.azan.Method
import com.azan.Time
import com.azan.astrologicalCalc.SimpleDate
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimModel
import com.zumo.fasttaqvim.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

object TimeHelper {
    @SuppressLint("SimpleDateFormat")
    fun prayerTime(location: Location, date: SimpleDate): TaqvimModel {

        val method = Method.NORTH_AMERICA
        method.madhhab = Madhhab.HANAFI
        val gmt = TimeZone.getDefault().rawOffset / 3600000.toDouble()
        val azanLocation =
            com.azan.astrologicalCalc.Location(location.latitude, location.longitude, gmt, 0)
        val azan = Azan(azanLocation, method)
        val prayerTimes = azan.getPrayerTimes(date)

        val today = SimpleDateFormat("dd").format(Date())

        if(today.toInt() == date.day)
            return TaqvimModel(
                closeMouth = Utils.timeFormatHourMinute(prayerTimes.fajr().toString()),
                openMouth = Utils.timeFormatHourMinute(prayerTimes.maghrib().toString()),
                bomdod = Utils.timeFormatHourMinute(prayerTimes.shuruq().toString()),
                peshin = Utils.timeFormatHourMinute(prayerTimes.thuhr().toString()),
                asr = Utils.timeFormatHourMinute(prayerTimes.assr().toString()),
                shom = Utils.timeFormatHourMinute(prayerTimes.maghrib().toString()),
                hufton = Utils.timeFormatHourMinute(prayerTimes.ishaa().toString()),
                today = true
            )
        else
            return TaqvimModel(
                closeMouth = Utils.timeFormatHourMinute(prayerTimes.fajr().toString()),
                openMouth = Utils.timeFormatHourMinute(prayerTimes.maghrib().toString()),
                bomdod = Utils.timeFormatHourMinute(prayerTimes.shuruq().toString()),
                peshin = Utils.timeFormatHourMinute(prayerTimes.thuhr().toString()),
                asr = Utils.timeFormatHourMinute(prayerTimes.assr().toString()),
                shom = Utils.timeFormatHourMinute(prayerTimes.maghrib().toString()),
                hufton = Utils.timeFormatHourMinute(prayerTimes.ishaa().toString()),
                today = false
            )
    }
}
