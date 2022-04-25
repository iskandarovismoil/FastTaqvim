package com.zumo.fasttaqvim

import android.Manifest
import android.animation.Animator
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.zumo.fasttaqvim.utils.Location.FINISH_FLAG
import com.zumo.fasttaqvim.utils.LOCATION_PERMISTON
import com.zumo.fasttaqvim.utils.LOCATION_REQ_CODE
import com.zumo.fasttaqvim.utils.Location.LocationHelper
import com.zumo.fasttaqvim.utils.Location.LocationPermission
import com.zumo.fasttaqvim.utils.Location.LocationPermissionAlert

class SplashActivity : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val window = this.window
        window.statusBarColor = Color.WHITE

        supportActionBar?.hide()

        locationHelper = LocationHelper(this)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onResume() {
        super.onResume()
        if (LocationPermission.secondLevelChecking(this)) {
            LocationPermissionAlert.showDialogSecondTime(this)
        }else if(LocationPermission.firstLevelChecking(this)){
            locationHelper.getLocationViaProviders()
            LOCATION_PERMISTON = true


            val logo : ImageView = findViewById(R.id.splashActivityLogoText)

            val intent = Intent(this, MainActivity::class.java)

            val anim_one = logo.animate().apply {
                startDelay = 500
                duration = 2000
                alphaBy(1f)
            }

            anim_one.setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {

                    startActivity(intent)
                    finish()

                }
            })
        }
        if(!LOCATION_PERMISTON && FINISH_FLAG == 1){
            LocationPermissionAlert.showDialogThirdTime(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LOCATION_PERMISTON = true
                locationHelper.getCurrentLocationViaNetworkProvider()
                locationHelper.getCurrentLocationViaGpsProvider()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    val showRational = shouldShowRequestPermissionRationale(permissions[0])
                    if (showRational) {
                        LocationPermissionAlert.showDialogSecondTime(this)
                    } else {
                        LocationPermissionAlert.showDialogThirdTime(this)
                        //here has some problems
                    }
                }
            }
        }
    }
}