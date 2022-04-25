package com.zumo.fasttaqvim.ui.home.taqvim.data

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zumo.fasttaqvim.utils.Coroutines
import kotlinx.coroutines.*


class TaqvimViewModel(
    private val taqvim: Taqvim
) : ViewModel() {

    private val taqvimlistMutable = MutableLiveData<ArrayList<TaqvimModel>>()
    private val todayMutable = MutableLiveData<TaqvimModel>()
    var resultLive: LiveData<ArrayList<TaqvimModel>> = taqvimlistMutable
    var todayLive: LiveData<TaqvimModel> = todayMutable
    private lateinit var job: Job

    fun getData(location: Location){

        job = Coroutines.ioThenMain(
            { taqvim.getData(location) },
            { taqvimlistMutable.value = it }
        )

    }

    fun getToday(location: Location){

        job = Coroutines.ioThenMain(
            { taqvim.getToday(location) },
            { todayMutable.value = it }
        )

    }


}