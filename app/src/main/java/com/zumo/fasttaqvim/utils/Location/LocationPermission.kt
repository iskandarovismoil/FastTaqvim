package com.zumo.fasttaqvim.utils.Location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.zumo.fasttaqvim.utils.LOCATION_REQ_CODE

object LocationPermission {

    private val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    fun firstLevelChecking(activity: Activity): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun secondLevelChecking(activity: Activity): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && (FINISH_FLAG == 1)
    }

    fun thirdLevelChecking(activity: Activity): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && (FINISH_FLAG == 0)
    }

    fun requestLitepermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            permission,
            LOCATION_REQ_CODE
        )
    }

    fun requestHardpermissions(activity: Activity) {
        activity.startActivity(
            Intent(
                Settings.ACTION_APPLICATION_SETTINGS
            )
        )
    }
}