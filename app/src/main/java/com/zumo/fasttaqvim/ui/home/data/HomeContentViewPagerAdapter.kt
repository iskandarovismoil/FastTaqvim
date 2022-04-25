package com.zumo.fasttaqvim.ui.home.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zumo.fasttaqvim.ui.home.read.ReadFragment
import com.zumo.fasttaqvim.ui.home.taqvim.TaqvimFragment

class HomeContentViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val COUNT = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ReadFragment()
            1 -> TaqvimFragment()
            else -> ReadFragment()
        }
    }

    override fun getCount(): Int {
        return COUNT
    }
}