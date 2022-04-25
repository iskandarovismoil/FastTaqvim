package com.zumo.fasttaqvim.ui.home.taqvim.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaqvimViewModelFactory: ViewModelProvider.Factory {

    private val TaqvimParse by lazy(LazyThreadSafetyMode.NONE){
        Taqvim()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaqvimViewModel(
            TaqvimParse
        ) as T
    }

}