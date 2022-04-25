package com.zumo.fasttaqvim.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun timeFormatHourMinute(time: String): String {
        val timeFormat = time.split(":")
        return timeFormat[0] + ":" + timeFormat[1]
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateBeauty(day: String): String {

        val mounths = arrayOf(
            "mounth",
            "Yanvar",
            "Fevral",
            "Mart",
            "Aprel",
            "May",
            "Iyun",
            "Iyul",
            "Avgust",
            "Sentabr",
            "Oktabr",
            "Noyabr",
            "Dekabr"
        )

        val mounth = SimpleDateFormat("M").format(Date())
        val year = SimpleDateFormat("yyyy").format(Date())

        return "$day, ${mounths.get(mounth.toInt())} $year"
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm")
        return timeFormat.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentYear(): String {
        val timeFormat = SimpleDateFormat("yyyy")
        return timeFormat.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentMonth(): String {
        val timeFormat = SimpleDateFormat("MM")
        return timeFormat.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDay(): String {
        val timeFormat = SimpleDateFormat("d")
        return timeFormat.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentHour(): String {
        val timeFormat = SimpleDateFormat("HH")
        return timeFormat.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentMinute(): String {
        val timeFormat = SimpleDateFormat("mm")
        return timeFormat.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentSecond(): String {
        val timeFormat = SimpleDateFormat("ss")
        return timeFormat.format(Date())
    }
}