package com.example.smarthome.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.example.smarthome.chart.adapter.ChartAdapter
import com.example.smarthome.history.adapter.HistoryAdapter
import com.example.smarthome.utils.FileHelper

class HistoryFragment : Fragment(), HistoryAdapter.OnNoteListener {

    private val listData = mutableListOf<String>()
    private val chartAdapter = HistoryAdapter(this, listData)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val file = FileHelper.readFromFile(requireContext())
        val a = 3
    }

    override fun onNoteClick(position: Int) {
        
    }
}