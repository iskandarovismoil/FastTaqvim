package com.zumo.fasttaqvim.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.card.MaterialCardView
import com.tmetjem.eduuz.ui.parts.students.details.TaqvimBottomSheet
import com.zumo.fasttaqvim.MainActivity
import com.zumo.fasttaqvim.R
import com.zumo.fasttaqvim.ui.home.data.HomeContentViewPagerAdapter
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimViewModel
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimViewModelFactory
import com.zumo.fasttaqvim.utils.LOCATION_PERMISTON
import com.zumo.fasttaqvim.utils.LOCATION_REQ_CODE
import com.zumo.fasttaqvim.utils.Location.*
import com.zumo.fasttaqvim.utils.Utils
import com.zumo.fasttaqvim.utils.Utils.getDateBeauty
import com.zumo.fasttaqvim.utils.Utils.timeFormatHourMinute
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var taqvimViewModel: TaqvimViewModel
    private lateinit var locationHelper: LocationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        locationHelper = LocationHelper(requireActivity())

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()


    }

    private fun setUpViews() {

        view?.findViewById<ImageButton>(R.id.homeLocationRefreshButton)?.setOnClickListener {
            locationHelper.getCurrentLocationViaNetworkProvider()
            locationHelper.getCurrentLocationViaGpsProvider()
            locationHelper.getLocationViaProviders()

            setUpHeader()
            setUpTodayData()
            setUpHomeContentViewPager()
        }

        setUpHeader()
        setUpTodayData()
        setUpHomeContentViewPager()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpTodayData() {

        val location = SavedLocation.get(requireContext())

        taqvimViewModel = ViewModelProvider(this, TaqvimViewModelFactory()).get(TaqvimViewModel::class.java)

        location?.let { taqvimViewModel.getToday(it) }

        taqvimViewModel.todayLive.observe(viewLifecycleOwner) {

            view?.findViewById<TextView>(R.id.closeMouthTime)?.text = timeFormatHourMinute(it.closeMouth)
            view?.findViewById<TextView>(R.id.openMouthTime)?.text = timeFormatHourMinute(it.openMouth)

            if (it.closeMouth.split(":")[0].toInt() > Utils.getCurrentHour().toInt()) {
                closeMouthPanel(true)
            } else {
                closeMouthPanel(false)
            }

            if (it.openMouth.split(":")[0].toInt() > Utils.getCurrentHour().toInt()) {
                openMouthPanel(true)
            } else {
                openMouthPanel(false)
            }

            val bottmoSheetData = it

            view?.findViewById<MaterialCardView>(R.id.homeTaqvimCardView)?.setOnClickListener {
                onClicK(
                    date = getCurrentDate(),
                    bomdod = bottmoSheetData.bomdod,
                    peshin = bottmoSheetData.peshin,
                    asr = bottmoSheetData.asr,
                    shom = bottmoSheetData.shom,
                    hufton = bottmoSheetData.hufton,
                    today = true
                )
            }
        }
    }

    private fun setUpHeader() {
        val handler = Handler()
        var r: Runnable ?= null

        view?.findViewById<TextView>(R.id.time)?.text = Utils.getCurrentTime()

        r = Runnable {
            run {
                view?.findViewById<TextView>(R.id.time)?.text = Utils.getCurrentTime()
                r?.let { handler.postDelayed(it, 60000) }
            }
        }

        val mill = 60000 - (Utils.getCurrentSecond().toInt() * 1000)
        handler.postDelayed(r, mill.toLong())

        view?.findViewById<TextView>(R.id.date)?.text = getCurrentDate()
    }

    private fun setUpHomeContentViewPager() {
        val homeContentViewPager = view?.findViewById<ViewPager>(R.id.homeContentViewPager)

        homeContentViewPager?.adapter = activity?.let { HomeContentViewPagerAdapter(it.supportFragmentManager) }
    }


    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        val day = SimpleDateFormat("dd").format(Date())

        return getDateBeauty(day)
    }

    private fun closeMouthPanel(active: Boolean) {
        if(active) {
            view?.findViewById<TextView>(R.id.closeMouthTime)?.setTextColor(Color.WHITE)
            view?.findViewById<TextView>(R.id.closeMouthTitle)?.setTextColor(Color.WHITE)
            view?.findViewById<LinearLayout>(R.id.closeMouthLayout)?.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#90be6d"))
        } else {
            view?.findViewById<TextView>(R.id.closeMouthTime)
                ?.setTextColor(Color.parseColor("#e3e3e3"))
            view?.findViewById<TextView>(R.id.closeMouthTitle)
                ?.setTextColor(Color.parseColor("#e3e3e3"))
            view?.findViewById<LinearLayout>(R.id.closeMouthLayout)?.setBackgroundColor(Color.WHITE)
        }
    }

    private fun openMouthPanel(active: Boolean) {
        if(active) {
            view?.findViewById<TextView>(R.id.openMouthTime)?.setTextColor(Color.WHITE)
            view?.findViewById<TextView>(R.id.openMouthTitle)?.setTextColor(Color.WHITE)
            view?.findViewById<LinearLayout>(R.id.openMouthLayout)?.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#90be6d"))
        } else {
            view?.findViewById<TextView>(R.id.openMouthTime)
                ?.setTextColor(Color.parseColor("#e3e3e3"))
            view?.findViewById<TextView>(R.id.openMouthTitle)
                ?.setTextColor(Color.parseColor("#e3e3e3"))
            view?.findViewById<LinearLayout>(R.id.openMouthLayout)?.setBackgroundColor(Color.WHITE)
        }
    }

    fun onClicK(date: String, bomdod: String, peshin: String, asr: String, shom: String, hufton: String, today: Boolean) {
        val bottomsheetdialog = TaqvimBottomSheet()
        bottomsheetdialog.date = date
        bottomsheetdialog.bomdod = bomdod
        bottomsheetdialog.peshin = peshin
        bottomsheetdialog.asr = asr
        bottomsheetdialog.shom = shom
        bottomsheetdialog.hufton = hufton
        bottomsheetdialog.today = today
        activity?.let { bottomsheetdialog.show(it.supportFragmentManager, "BottomSheetDialog") }
    }
}