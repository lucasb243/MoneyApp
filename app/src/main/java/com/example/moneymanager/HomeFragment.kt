package com.example.moneymanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class HomeFragment:Fragment(R.layout.fragment_home) {

    private val MAX_X_VALUE = 7
    private val MAX_Y_VALUE = 50
    private val MIN_Y_VALUE = 5
    private val SET_LABEL = "App Downloads"
    private val DAYS = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    private lateinit var barChart:BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        barChart = view.findViewById(R.id.bcBarChar)
        var data:BarData? = createChartData()
        return view
    }

    private fun createChartData(): BarData? {
        val values: ArrayList<BarEntry> = ArrayList()
        for (i in 0 until MAX_X_VALUE) {
            val x = i.toFloat()
            val y: Float = 10f
            values.add(BarEntry(x, y))
        }
        val set1 = BarDataSet(values, SET_LABEL)
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set1)
        return BarData(dataSets)
    }

    private fun configureChartAppearance() {
        barChart.getDescription().setEnabled(false)
        barChart.setDrawValueAboveBar(false)
        val xAxis: XAxis = barChart.getXAxis()
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return DAYS[value.toInt()]
            }
        }
        val axisLeft: YAxis = barChart.getAxisLeft()
        axisLeft.granularity = 10f
        axisLeft.axisMinimum = 0f
        val axisRight: YAxis = barChart.getAxisRight()
        axisRight.granularity = 10f
        axisRight.axisMinimum = 0f
    }
    private fun prepareChartData(data: BarData) {
        data.setValueTextSize(12f)
        barChart.setData(data)
        barChart.invalidate()
    }
}