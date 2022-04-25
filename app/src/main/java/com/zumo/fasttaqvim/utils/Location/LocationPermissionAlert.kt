package com.zumo.fasttaqvim.utils.Location

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.location.LocationManager
import kotlin.system.exitProcess

object LocationPermissionAlert {

    private lateinit var dialog: AlertDialog

    fun showDialogFirstTime(activity: Activity) {
        if (this::dialog.isInitialized)
            dialog.dismiss()

        dialog = AlertDialog.Builder(activity)
            .setMessage("Ilova ishlashi uchun sizning joylashuvingizni bilishimiz zarur!")
            .setCancelable(false)
            .setPositiveButton(
                "Ruxsat berish"
            ) { _, _ ->
                LocationPermission.requestLitepermissions(activity)
            }
            .setOnDismissListener {
                it.dismiss()
            }
            .create()
        dialog.show()
    }

    fun showDialogSecondTime(activity: Activity) {
        if (this::dialog.isInitialized)
            dialog.dismiss()

        dialog = AlertDialog.Builder(activity)
            .setMessage(
                "Joylashuvingiz uchun ruhsat bering. Joylashuvigizni bilgan holda biz sizga" +
                        " namoz vaqtlarini hisoblab beramiz. Busiz ilova umuman ishlamaydi!"
            )
            .setCancelable(false)
            .setPositiveButton(
                "Ruxsat berish"
            ) { _, _ ->
                LocationPermission.requestLitepermissions(activity)
            }
            .setNegativeButton("Qolish") { _: DialogInterface, _: Int ->
                FINISH_FLAG = 1
                exitProcess(0)
            }
            .setOnDismissListener {
                it.dismiss()
            }
            .create()
        dialog.show()
    }

    fun showDialogThirdTime(activity: Activity) {
        if (this::dialog.isInitialized)
            dialog.dismiss()

        dialog = AlertDialog.Builder(activity)
            .setMessage("Sozlamalar bo'limiga kirib, ushbu ilovani topib, ruxsatlar ichidan joylashuvga ruxsat bering!")
            .setCancelable(false)
            .setPositiveButton(
                "Sozlamalarni ochish"
            ) { _, _ ->
                LocationPermission.requestHardpermissions(activity)
            }
            .setNegativeButton("Qolish") { _: DialogInterface, _: Int ->
                FINISH_FLAG = 1
                exitProcess(0)
            }
            .setOnDismissListener {
                it.dismiss()
            }
            .create()
        dialog.show()
    }

    fun showDialogGpsCheck(activity: Activity) {
        if (this::dialog.isInitialized)
            dialog.dismiss()

        dialog = AlertDialog.Builder(activity)
            .setMessage("GPS yoqilmagan. Iltimos ilova to'g'ri ishlashi uchun GPSni yoqishingizni iltimos qilamiz.")
            .setCancelable(false)
            .setPositiveButton(
                "Sozlamalarni ochish"
            ) { _, _ ->
                LocationPermission.requestHardpermissions(activity)
            }
            .setOnDismissListener {
                it.dismiss()
            }
            .create()
        dialog.show()
    }

}