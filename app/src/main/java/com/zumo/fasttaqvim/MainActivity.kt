package com.zumo.fasttaqvim

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import java.util.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val notification = Notification()
//        notification.createNotificationChannel(
//            context = applicationContext,
//            notificationChannelId = "alarm",
//            notificationChannelName = "Alarm",
//            notificationChannelDescription = "Take alarm to prayer"
//        )
//
//        val taqvim = TaqvimNotification()
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val prayer = taqvim.getNextPrayerAlarm(applicationContext)
//
//            notification.createNotification(
//                context = applicationContext,
//                notificationTitle = "Namoz vaqti",
//                notificationMessage = prayer.name+" namozi vaqti boldi",
//                notificationChannelId = "alarm",
//                notificationId = 1,
//                notificationTimer = prayer.time!!
//            )
//        }
    }
}