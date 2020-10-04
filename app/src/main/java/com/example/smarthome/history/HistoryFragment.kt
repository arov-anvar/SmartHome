package com.example.smarthome.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.example.smarthome.history.adapter.HistoryAdapter
import com.example.smarthome.history.model.ResultEntity
import com.example.smarthome.utils.FileHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_history.*
import org.json.JSONObject

class HistoryFragment : Fragment(), HistoryAdapter.OnNoteListener {

    private val listData = mutableListOf<String>()
    private val chartAdapter = HistoryAdapter(this, listData)
    private val mGson = Gson()

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
        val dataString = FileHelper.readFromFile(requireContext()) ?: ""
        val result = mGson.fromJson(dataString, ResultEntity::class.java)
        for (value in result.data) {
            textView.text = value.name + ":" + textView.text
        }
    }

    override fun onNoteClick(position: Int) {

    }
}