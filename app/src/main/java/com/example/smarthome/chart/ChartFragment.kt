package com.example.smarthome.chart

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smarthome.R
import com.example.smarthome.chart.adapter.ChartAdapter
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_charts.*
import kotlin.random.Random

class ChartFragment : Fragment() {

    private val nameCharts = listOf(
        "Датчик температуры",
        "Датчик влажности",
        "Датчик освещения",
        "Датчик дыма",
        "Датчик звука"
    )

    private val listMaxValue = listOf(
        100,
        100,
        100,
        100,
        100
    )

    private val listLineSetValue = mutableListOf<LineDataSet>()
    private val listEntry = listOf<MutableList<Entry>>(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
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

        startDrawData()
    }

    private fun startDrawData() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                for (i in 0..4) {
                    listEntry[i].add(getRandomEntry(listMaxValue[i]))
                    listData[i].addDataSet(LineDataSet(listEntry[i], nameCharts[i]))
                    listData[i].notifyDataChanged()
                    chartAdapter.notifyDataSetChanged()
                }
                ++indexTime
                if (isUpdateData) handler.postDelayed(this, 700)
            }
        }, 700)
    }

    private fun initPipeChartData(){
        for (i in 0..4) {
            listEntry[i].add(getRandomEntry(listMaxValue[i]))
            val setValue = LineDataSet(listEntry[i], nameCharts[i])
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

    private fun getRandomEntry(maxValue: Int): Entry =
        Entry(indexTime.toFloat(), Random.nextInt(0, maxValue).toFloat())

}