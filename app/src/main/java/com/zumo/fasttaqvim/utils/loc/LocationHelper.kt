package com.zumo.fasttaqvim.utils.loc


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zumo.fasttaqvim.utils.ALL_PREFS


var FINISH_FLAG = 0


class LocationHelper(private val activity: Activity) {

    private lateinit var dialog: AlertDialog
    private lateinit var prefs: SharedPreferences
    private lateinit var locationManager: LocationManager
    private var location: Location? = null
    private val locationObservable = MutableLiveData<Location>()

    init {
        initialize()
    }

    private fun initialize() {

        prefs = activity.getSharedPreferences(ALL_PREFS, Context.MODE_PRIVATE)

        locationManager =
            (activity.getSystemService(LOCATION_SERVICE) as LocationManager?)!!

        if (LocationPermission.secondLevelChecking(activity)) {
            LocationPermissionAlert.showDialogSecondTime(activity)
        } else if (LocationPermission.thirdLevelChecking(activity)
        ) {
            LocationPermissionAlert.showDialogFirstTime(activity)
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(locationResult: Location) {
            if (location != null) {
                    location = locationResult
                    locationObservable.value = locationResult
                    SavedLocation.set(activity, location!!)
            } else {
                location = locationResult
                locationObservable.value = locationResult
                SavedLocation.set(activity, location!!)
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun getLocation(): LiveData<Location> {
        return locationObservable
    }

    @SuppressLint("MissingPermission")
    fun showDialogGpsCheck() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationPermissionAlert.showDialogGpsCheck(activity)
        } else {
            getCurrentLocationViaNetworkProvider()
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                50000f,
                locationListener
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocationViaNetworkProvider() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                50000f,
                locationListener
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocationViaGpsProvider(){
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                50000f,
                locationListener
            )
        }else {
            showDialogGpsCheck()
        }
    }



    fun dialogDismiss() {
        if (this::dialog.isInitialized)
            dialog.dismiss()
    }

    @SuppressLint("MissingPermission")
    fun getLocationViaProviders() {
        prefs = activity.getSharedPreferences(ALL_PREFS, Context.MODE_PRIVATE)
        locationManager =
            (activity.getSystemService(LOCATION_SERVICE) as LocationManager?)!!
        val providers = locationManager.getProviders(true)

        for (i in providers.indices.reversed()) {
            location = locationManager.getLastKnownLocation(providers[i])
        }

        if (location != null) {
            locationObservable.value = location!!
            SavedLocation.set(activity, location!!)
        } else {
            if (LocationPermission.firstLevelChecking(activity))
                getCurrentLocationViaNetworkProvider()
            else
                LocationPermissionAlert.showDialogFirstTime(activity)
        }
    }

}
