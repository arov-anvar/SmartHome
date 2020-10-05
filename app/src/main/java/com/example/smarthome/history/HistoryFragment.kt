package com.example.smarthome.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.example.smarthome.chart.ChartFragment
import com.example.smarthome.history.adapter.HistoryAdapter
import com.example.smarthome.history.model.ResultEntity
import com.example.smarthome.history.result.ResultFragment
import com.example.smarthome.utils.FileHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment(), HistoryAdapter.OnNoteListener {

    private val listData = mutableListOf<String>()
    private val chartAdapter = HistoryAdapter(this, listData)
    private val mGson = Gson()
    private lateinit var result: ResultEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameRV.adapter = chartAdapter
        val dataString = FileHelper.readFromFile(requireContext()) ?: ""
        result = mGson.fromJson(dataString, ResultEntity::class.java)

        listData.clear()
        for (value in result.data) {
            listData.add(value.name)
        }
        chartAdapter.notifyDataSetChanged()
    }

    override fun onNoteClick(position: Int) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.navHost, ResultFragment(result.data[position]))?.addToBackStack("history")?.commit()
    }
}