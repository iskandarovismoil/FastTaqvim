package com.zumo.fasttaqvim.utils.Location

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import com.zumo.fasttaqvim.utils.ALL_PREFS
import com.zumo.fasttaqvim.utils.LAST_LOCATION_UPDATE
import com.zumo.fasttaqvim.utils.LATITUDE
import com.zumo.fasttaqvim.utils.LONGITUDE

object SavedLocation {

    private lateinit var prefs: SharedPreferences

    fun get(context: Context): Location? {
        prefs = context.getSharedPreferences(ALL_PREFS, Context.MODE_PRIVATE)
        val latitudeSt = prefs.getString(LATITUDE, null)
        val longtitudeSt = prefs.getString(LONGITUDE, null)
        var location: Location? = null
        if (latitudeSt != null && longtitudeSt != null) {
            location = Location("")
            location.latitude = latitudeSt.toDouble()
            location.longitude = longtitudeSt.toDouble()
        }

        return location
    }

    fun set(activity: Activity, location: Location) {
        prefs = activity.getSharedPreferences(ALL_PREFS, Context.MODE_PRIVATE)

        prefs.edit()
            .putString(LATITUDE, location.latitude.toString())
            .putString(LONGITUDE, location.longitude.toString())
            .putLong(LAST_LOCATION_UPDATE, System.currentTimeMillis())
            .apply()
    }
}