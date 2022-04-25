package com.tmetjem.eduuz.ui.parts.students.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zumo.fasttaqvim.R
import com.zumo.fasttaqvim.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class TaqvimBottomSheet : BottomSheetDialogFragment() {

    var date: String? = null
    var bomdod: String? = null
    var peshin: String? = null
    var asr: String? = null
    var shom: String? = null
    var hufton: String? = null
    var today: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.taqvim_bottom_sheet, container, false)
        view.findViewById<TextView>(R.id.TaqvimDate).text = date

        if(today == true)
            active(view.findViewById(R.id.TaqvimDate))

        bomdod?.let {
            checkTodayLastTime(
                it,
                view.findViewById(R.id.TaqvimBomdodTitle),
                view.findViewById(R.id.TaqvimBomdodText),
            )
        }

        peshin?.let {
            checkTodayLastTime(
                it,
                view.findViewById(R.id.TaqvimPeshinTitle),
                view.findViewById(R.id.TaqvimPeshinText),
            )
        }

        asr?.let {
            checkTodayLastTime(
                it,
                view.findViewById(R.id.TaqvimAsrTitle),
                view.findViewById(R.id.TaqvimAsrText),
            )
        }

        shom?.let {
            checkTodayLastTime(
                it,
                view.findViewById(R.id.TaqvimShomTitle),
                view.findViewById(R.id.TaqvimShomText),
            )
        }

        hufton?.let {
            checkTodayLastTime(
                it,
                view.findViewById(R.id.TaqvimHuftonTitle),
                view.findViewById(R.id.TaqvimHuftonText),
            )
        }
//        if(today == true && getCurrentHour().toInt() > bomdod!!.split(":")[0].toInt()){
//            disable(view.findViewById(R.id.TaqvimBomdodText))
//            disable(view.findViewById(R.id.TaqvimBomdodTitle))
//        } else if(today == true) {
//            active(view.findViewById(R.id.TaqvimBomdodText))
//            active(view.findViewById(R.id.TaqvimBomdodTitle))
//        }
//
//        if(today == true && getCurrentHour().toInt() > peshin!!.split(":")[0].toInt()) {
//            disable(view.findViewById(R.id.TaqvimPeshinText))
//            disable(view.findViewById(R.id.TaqvimPeshinTitle))
//        } else if(today == true) {
//            active(view.findViewById(R.id.TaqvimPeshinText))
//            active(view.findViewById(R.id.TaqvimPeshinTitle))
//        }
//
//        if(today == true && getCurrentHour().toInt() > asr!!.split(":")[0].toInt()) {
//            disable(view.findViewById(R.id.TaqvimAsrText))
//            disable(view.findViewById(R.id.TaqvimAsrTitle))
//        } else if(today == true) {
//            active(view.findViewById(R.id.TaqvimAsrText))
//            active(view.findViewById(R.id.TaqvimAsrTitle))
//        }
//
//        if(today == true && getCurrentHour().toInt() > shom!!.split(":")[0].toInt()) {
//            disable(view.findViewById(R.id.TaqvimShomText))
//            disable(view.findViewById(R.id.TaqvimShomTitle))
//        } else if(today == true) {
//            active(view.findViewById(R.id.TaqvimShomText))
//            active(view.findViewById(R.id.TaqvimShomTitle))
//        }
//
//        if(today == true && getCurrentHour().toInt() > hufton!!.split(":")[0].toInt()) {
//            disable(view.findViewById(R.id.TaqvimHuftonText))
//            disable(view.findViewById(R.id.TaqvimHuftonTitle))
//        } else if(today == true) {
//            active(view.findViewById(R.id.TaqvimHuftonText))
//            active(view.findViewById(R.id.TaqvimHuftonTitle))
//        }

        if (date!!.split(",")[0].toInt() < Utils.getCurrentDay().toInt()){
            disable(view.findViewById(R.id.TaqvimBomdodText))
            disable(view.findViewById(R.id.TaqvimBomdodTitle))

            disable(view.findViewById(R.id.TaqvimPeshinText))
            disable(view.findViewById(R.id.TaqvimPeshinTitle))

            disable(view.findViewById(R.id.TaqvimAsrText))
            disable(view.findViewById(R.id.TaqvimAsrTitle))

            disable(view.findViewById(R.id.TaqvimShomText))
            disable(view.findViewById(R.id.TaqvimShomTitle))

            disable(view.findViewById(R.id.TaqvimHuftonText))
            disable(view.findViewById(R.id.TaqvimHuftonTitle))

            disable(view.findViewById(R.id.TaqvimDate))
        }

        view.findViewById<TextView>(R.id.TaqvimBomdodText).text = bomdod
        view.findViewById<TextView>(R.id.TaqvimPeshinText).text = peshin
        view.findViewById<TextView>(R.id.TaqvimAsrText).text = asr
        view.findViewById<TextView>(R.id.TaqvimShomText).text = shom
        view.findViewById<TextView>(R.id.TaqvimHuftonText).text = hufton

        return view
    }

    private fun disable(textview: TextView) {
        textview.setTextColor(Color.parseColor("#e3e3e3"))
    }

    private fun active(textview: TextView) {
        textview.setTextColor(Color.parseColor("#CBF94144"))
    }

    fun checkTodayLastTime(time: String, titleTextView: TextView, textTextView: TextView){
        if(today == true) {

            active(titleTextView)
            active(textTextView)

            val times = time.split(":")

            if(Utils.getCurrentHour().toInt() == times[0].toInt()) {
                if(Utils.getCurrentMinute().toInt() > times[1].toInt()) {
                    disable(titleTextView)
                    disable(textTextView)
                }
            } else if(Utils.getCurrentHour().toInt() > times[0].toInt()) {
                disable(titleTextView)
                disable(textTextView)
            }
        }
    }
}
