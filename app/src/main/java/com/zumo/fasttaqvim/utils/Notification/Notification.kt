package com.zumo.fasttaqvim.utils.Notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.azan.astrologicalCalc.SimpleDate
import com.zumo.fasttaqvim.MainActivity
import com.zumo.fasttaqvim.R
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimModel
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimViewModel
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimViewModelFactory
import com.zumo.fasttaqvim.ui.home.taqvim.notification.TaqvimNotification
import com.zumo.fasttaqvim.utils.Location.SavedLocation
import com.zumo.fasttaqvim.utils.Time.TimeHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver()
{
    private lateinit var taqvimViewModel: TaqvimViewModel

    override fun onReceive(context: Context, intent: Intent)
    {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val notificationChannelId = intent.getStringExtra("notificationChannelId")

        val notification = NotificationCompat.Builder(context, notificationChannelId?:"")
            .setSmallIcon(R.drawable.ic_logo_text)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)

        val taqvimNotification = TaqvimNotification()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val prayer = taqvimNotification.getNextPrayerAlarm(context)

            createNotification(
                context = context,
                notificationTitle = "Namoz vaqti",
                notificationMessage = prayer.name+" namozi vaqti boldi",
                notificationChannelId = "alarm",
                notificationId = 1,
                notificationTimer = prayer.time!!
            )
        }
    }


    fun createNotification(
        context: Context,
        notificationTitle: String,
        notificationMessage: String,
        notificationChannelId: String,
        notificationId: Int,
        notificationTimer: Long
    ) {

        val intent = Intent(context, this::class.java)

        intent.putExtra(titleExtra, notificationTitle)
        intent.putExtra(messageExtra, notificationMessage)
        intent.putExtra("notificationChannelId", notificationChannelId)
        intent.putExtra("notificationId", notificationId)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = notificationTimer

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }else alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }

    fun cancelNotification(context: Context, notificationId: Int) {
        val intent = Intent(context, Notification::class.java)
        val pIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.cancel(pIntent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun createNotificationChannel(context: Context, notificationChannelId: String, notificationChannelName: String, notificationChannelDescription: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId, notificationChannelName, importance)
            channel.description = notificationChannelDescription

            val notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}