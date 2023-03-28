package com.zumo.fasttaqvim.ui.home.taqvim

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tmetjem.eduuz.ui.parts.students.details.TaqvimBottomSheet
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimInterface
import com.zumo.fasttaqvim.R
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimAdapter
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimViewModel
import com.zumo.fasttaqvim.ui.home.taqvim.data.TaqvimViewModelFactory
import com.zumo.fasttaqvim.utils.loc.SavedLocation

class TaqvimFragment : Fragment(), TaqvimInterface {

    private lateinit var taqvimViewModel: TaqvimViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taqvim, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    fun setUpViews() {

        val location = SavedLocation.get(requireContext())

        taqvimViewModel = ViewModelProvider(this, TaqvimViewModelFactory())[TaqvimViewModel::class.java]

        location?.let { taqvimViewModel.getData(it) }

        taqvimViewModel.resultLive.observe(viewLifecycleOwner) {

            val TaqvimRecyclerView = view?.findViewById<RecyclerView>(R.id.TaqvimRecyclerView)
            TaqvimRecyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            TaqvimRecyclerView?.adapter = TaqvimAdapter(it, this@TaqvimFragment)

        }

    }

    override fun onClick(date: String, bomdod: String, peshin: String, asr: String, shom: String, hufton: String, today: Boolean) {
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