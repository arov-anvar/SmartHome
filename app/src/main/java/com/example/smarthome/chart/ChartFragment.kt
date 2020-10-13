package com.example.smarthome.chart

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smarthome.R
import com.example.smarthome.utils.Settings
import com.example.smarthome.chart.adapter.ChartAdapter
import com.example.smarthome.history.model.Data
import com.example.smarthome.history.model.ResultEntity
import com.example.smarthome.utils.FileHelper
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_charts.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random


class ChartFragment : Fragment() {

    private val listMinValue = listOf(
        -29,
        0,
        0,
        0,
        0
    )

    private val listMaxValue = listOf(
        43,
        100,
        100,
        10,
        150
    )

    private val listLineSetValue = mutableListOf<LineDataSet>()
    private val listEntry = listOf<MutableList<Entry>>(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )
    private val listData = mutableListOf<LineData>()
    private var indexTime = 0
    private val chartAdapter = ChartAdapter(listData)
    private var isUpdateData = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPipeChartData()
        chartRV.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        chartRV.adapter = chartAdapter
        chartRV.scrollToPosition(0)

        isUpdateDataCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isUpdateData = true
                startDrawData()
            } else {
                isUpdateData = false
            }
        }

        saveDataBtn.setOnClickListener {
            val nameEditText = EditText(requireContext())
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Введите имя для сохранения")
                .setMessage("На английском без пробелов")
                .setView(nameEditText)
                .setPositiveButton("сохранить") { dialog, which ->
                    saveData(nameEditText.text.toString())
                }
                .setNegativeButton("отмена", null)
                .create()
            dialog.show()
        }
    }

    private fun saveData(nameData: String) {
        val jsonObject = JSONObject()
        val jsonArray = JSONArray()
        listEntry.forEach {
            val jsonArrayFloat = JSONArray()
            it.forEach {entry ->
                jsonArrayFloat.put(entry.y)
            }
            jsonArray.put(jsonArrayFloat)
        }
        jsonObject.put("name", nameData)
        jsonObject.put("value", jsonArray)


        val currentData = FileHelper.readFromFile(requireContext()) ?: ""
        if (currentData.isEmpty()) {
            val rootObject = JSONObject()
            rootObject.put("data", JSONArray().put(jsonObject))
            FileHelper.writeToFile(rootObject.toString(), requireContext())
        }
        else {
            val dataString = FileHelper.readFromFile(requireContext()) ?: ""
            val mGson = Gson()
            val result = mGson.fromJson(dataString, ResultEntity::class.java)
            val newData = mGson.fromJson(jsonObject.toString(), Data::class.java)
            result.data.add(newData)
            val newStrData = mGson.toJson(result)
            FileHelper.writeToFile(newStrData, requireContext())
        }
    }

    private fun startDrawData() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                for (i in 0..4) {
                    listEntry[i].add(getRandomEntry(listMinValue[i], listMaxValue[i]))
                    listData[i].clearValues()
                    listData[i].addDataSet(LineDataSet(listEntry[i], Settings.nameCharts[i]))
                    listData[i].notifyDataChanged()
                    chartAdapter.notifyDataSetChanged()
                }
                ++indexTime
                if (isUpdateData) handler.postDelayed(this, 700)
            }
        }, 700)
    }

    private fun initPipeChartData() {
        for (i in 0..4) {
            listEntry[i].add(getRandomEntry(listMinValue[i], listMaxValue[i]))
            val setValue = LineDataSet(listEntry[i], Settings.nameCharts[i])
            setValue.axisDependency = YAxis.AxisDependency.LEFT
            setValue.color = ColorTemplate.getHoloBlue()
            setValue.valueTextColor = ColorTemplate.getHoloBlue()
            setValue.lineWidth = 3f
            setValue.setDrawCircles(false)
            setValue.setDrawValues(false)
            setValue.fillAlpha = 65
            setValue.fillColor = ColorTemplate.getHoloBlue()
            setValue.highLightColor = Color.rgb(244, 117, 117)
            setValue.setDrawCircleHole(true)
            listLineSetValue.add(setValue)
            val data = LineData(setValue)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)
            listData.add(data)
        }
        ++indexTime
    }

    private fun getRandomEntry(minValue: Int, maxValue: Int): Entry =
        Entry(indexTime.toFloat(), Random.nextInt(minValue, maxValue).toFloat())

}