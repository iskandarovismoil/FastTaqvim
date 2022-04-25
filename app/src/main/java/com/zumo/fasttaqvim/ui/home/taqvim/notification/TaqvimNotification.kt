package com.zumo.fasttaqvim.ui.home.taqvim.notification

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.azan.astrologicalCalc.SimpleDate
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimModel
import com.zumo.fasttaqvim.utils.Location.SavedLocation
import com.zumo.fasttaqvim.utils.Time.TimeHelper
import com.zumo.fasttaqvim.utils.Utils
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class TaqvimNotification {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun getNextPrayerAlarm(context: Context): TaqvimNotificationModel {
        val prayer = getNextPrayer(context)

        return prayer
    }

    @SuppressLint("SimpleDateFormat")
    fun milliseconds(date: String?): Long {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
        try {
            val mDate = sdf.parse(date)
            val timeInMilliseconds = mDate.time
            println("Date in milli :: $timeInMilliseconds")
            return timeInMilliseconds
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextPrayer(context: Context): TaqvimNotificationModel {
        val todayPrayers = getTodayPrayers(context)

        if (checkTodayPrayers(todayPrayers)) {
            val day = Utils.getCurrentDay()
            val month = Utils.getCurrentMonth()
            val year = Utils.getCurrentYear()

            if (checkToCurrentTime(todayPrayers.bomdod))
                return TaqvimNotificationModel(
                    name = "Bomdod",
                    time =  milliseconds(day+"-"+month+"-"+year+" "+todayPrayers.bomdod)
                )

            if (checkToCurrentTime(todayPrayers.peshin))
                return TaqvimNotificationModel(
                    name = "Peshin",
                    time = milliseconds(day+"-"+month+"-"+year+" "+todayPrayers.peshin)
                )

            if (checkToCurrentTime(todayPrayers.asr))
                return TaqvimNotificationModel(
                    name = "Asr",
                    time = milliseconds(day+"-"+month+"-"+year+" "+todayPrayers.asr)
                )

            if (checkToCurrentTime(todayPrayers.shom))
                return TaqvimNotificationModel(
                    name = "Shom",
                    time = milliseconds(day+"-"+month+"-"+year+" "+todayPrayers.shom)
                )

            if (checkToCurrentTime(todayPrayers.hufton))
                return TaqvimNotificationModel(
                    name = "Hufton",
                    time = milliseconds(day+"-"+month+"-"+year+" "+todayPrayers.hufton)
                )
        } else {
            val nextDayPrayers = getNextDayPrayers(context)

            val today: LocalDate = LocalDate.now()
            val tomorrow: LocalDate = today.plusDays(1)
            val day = tomorrow.dayOfMonth
            val month = tomorrow.monthValue
            val year = tomorrow.year

            return  TaqvimNotificationModel(
                name = "Bomdod",
                time = milliseconds(day.toString()+"-"+month+"-"+year+" "+nextDayPrayers.bomdod)
            )
        }

        return TaqvimNotificationModel(
            name = "",
            time = null
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayPrayers(context: Context): TaqvimModel {
        val location = SavedLocation.get(context)

        val day = SimpleDateFormat("dd").format(Date())
        val mounth = SimpleDateFormat("M").format(Date())
        val year = SimpleDateFormat("yyyy").format(Date())

        val prayer = location.let {
            TimeHelper.prayerTime(location = it!!, date = SimpleDate(
                day.toInt(),
                mounth.toInt(),
                year.toInt())
            )
        }

        return prayer
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextDayPrayers(context: Context): TaqvimModel {
        val today: LocalDate = LocalDate.now()
        val tomorrow: LocalDate = today.plusDays(1)
        val day = tomorrow.dayOfMonth
        val month = tomorrow.monthValue
        val year = tomorrow.year

        val location = SavedLocation.get(context)

        val prayer = location.let {
                TimeHelper.prayerTime(location = it!!, date = SimpleDate(
                    day,
                    month,
                    year
                )
            )
        }

        return prayer
    }

    private fun checkTodayPrayers(prayer: TaqvimModel): Boolean {
        if(checkToCurrentTime(prayer.bomdod))
            return true

        if (checkToCurrentTime(prayer.peshin))
            return true

        if (checkToCurrentTime(prayer.asr))
            return true

        if (checkToCurrentTime(prayer.shom))
            return true

        if (checkToCurrentTime(prayer.hufton))
            return true

        return false
    }

    private fun checkToCurrentTime(time: String): Boolean {

        val currentMills = System.currentTimeMillis()

        val prayerMills = milliseconds(
            "${Utils.getCurrentDay()}-${Utils.getCurrentMonth()}-${Utils.getCurrentYear()} ${time}"
        )

        if(currentMills > prayerMills)
            return false

        return true
    }

}