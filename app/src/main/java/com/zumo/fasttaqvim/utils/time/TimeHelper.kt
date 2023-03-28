package com.zumo.fasttaqvim.utils.time

import android.annotation.SuppressLint
import android.location.Location
import com.azan.Azan
import com.azan.AzanTimes
import com.azan.Madhhab
import com.azan.Method
import com.azan.astrologicalCalc.SimpleDate
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimModel
import java.util.*

object TimeHelper {
    @SuppressLint("SimpleDateFormat")
    fun prayerTime(location: Location, date: SimpleDate): AzanTimes {

        val method = Method.NORTH_AMERICA
        method.madhhab = Madhhab.HANAFI
        val gmt = TimeZone.getDefault().rawOffset / 3600000.toDouble()
        val location =
            com.azan.astrologicalCalc.Location(location.latitude, location.longitude, gmt, 0)
        val azan = Azan(location, method)

        return azan.getPrayerTimes(date)
    }
}
