package com.zumo.fasttaqvim

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.NavHostFragment
import com.azan.astrologicalCalc.SimpleDate
import com.zumo.fasttaqvim.ui.home.taqvim.notification.TaqvimNotification
import com.zumo.fasttaqvim.utils.DEFAULT_LOCATION
import com.zumo.fasttaqvim.utils.Location.SavedLocation
import com.zumo.fasttaqvim.utils.Notification.Notification
import com.zumo.fasttaqvim.utils.Time.TimeHelper
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notification = Notification()
        notification.createNotificationChannel(
            context = applicationContext,
            notificationChannelId = "alarm",
            notificationChannelName = "Alarm",
            notificationChannelDescription = "Take alarm to prayer"
        )

        val taqvim = TaqvimNotification()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val prayer = taqvim.getNextPrayerAlarm(applicationContext)

            notification.createNotification(
                context = applicationContext,
                notificationTitle = "Namoz vaqti",
                notificationMessage = prayer.name+" namozi vaqti boldi",
                notificationChannelId = "alarm",
                notificationId = 1,
                notificationTimer = prayer.time!!
            )
        }
    }
}