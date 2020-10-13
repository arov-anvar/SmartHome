package com.example.smarthome.history.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.example.smarthome.utils.Settings
import com.example.smarthome.history.model.Data
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment(private val mData: Data) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTxt.text = mData.name

        for ((i, value) in mData.value.withIndex()) {
            resultsTxt.text = "${resultsTxt.text} ${Settings.nameCharts[i]}: \n $value \n \n"
        }
    }
}