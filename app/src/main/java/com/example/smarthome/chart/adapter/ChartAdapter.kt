package com.example.smarthome.chart.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smarthome.R

import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData

class ChartAdapter(private val mData: MutableList<LineData>) : RecyclerView.Adapter<ChartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chart_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pipeChart = holder.itemView.findViewById<LineChart>(R.id.pipeChart)
        pipeChart.data = mData[position]
    }

    fun updateData() {
        notifyDataSetChanged()
    }
}