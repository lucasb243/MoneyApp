package com.example.moneymanager

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.Observer
import java.util.logging.Filter


class SetListFilters(var transactionDB: SQLiteDatabase, var adapter: TransactionAdapter):Fragment(R.layout.fragment_set_list_filters) {

    private lateinit var gridView           : GridView
    private lateinit var btnYearFilter      : Button
    private lateinit var btnMonthFilter     : Button
    private lateinit var btnWeekFilter      : Button
    private lateinit var btnRevenueFilter   : Button
    private lateinit var btnExpeneseFilter  : Button
    private lateinit var btnNeutralFilter   : Button
    private lateinit var btnSbmtFilter      : Button
    private lateinit var tvShowAmountFilter : TextView
    private lateinit var sbAmountFilters    : SeekBar

    private lateinit var viewModel          : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_list_filters, container, false)

        setUpViews(view)
        setUpButtonOnclick()
        setUpSeekbar()

        val arrayAdapter: ArrayAdapter<CategoryEnum> = ArrayAdapter<CategoryEnum>(activity!!, android.R.layout.simple_list_item_multiple_choice, CategoryEnum.values())

        gridView.setAdapter(arrayAdapter)

        // When the user clicks on the GridItem
        /*gridView.setOnItemClickListener(OnItemClickListener { a, v, position, id ->
            val o: Any = gridView.getItemAtPosition(position)
        })*/


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    private fun getAllItemsNeu(): Cursor {
        val selArgs = arrayOf("r")
        return transactionDB!!.query(
                TransactionList.TransactionEntry.TABLE_NAME,
                null,
                "type = ?",
                selArgs,
                null,
                null,
                TransactionList.TransactionEntry.COLUMN_CREATEDAT + " DESC")
    }

    private fun setUpViews(view: View){
        gridView            = view.findViewById(R.id.gvCategoryFilter )
        btnExpeneseFilter   = view.findViewById(R.id.btnExpenseFilter)
        btnMonthFilter      = view.findViewById(R.id.btnMonthFilter)
        btnWeekFilter       = view.findViewById(R.id.btnWeekFilter)
        btnNeutralFilter    = view.findViewById(R.id.btnNeutralFilter)
        btnRevenueFilter    = view.findViewById(R.id.btnRevenueFilter)
        btnSbmtFilter       = view.findViewById(R.id.btnSbmtFilter)
        btnYearFilter       = view.findViewById(R.id.btnYearFilter)
        sbAmountFilters     = view.findViewById(R.id.seekBar)
        tvShowAmountFilter  = view.findViewById(R.id.tvFilterAmount)
    }

    private fun setUpButtonOnclick(){
        btnExpeneseFilter.setOnClickListener { changeButtonAppearance(btnExpeneseFilter) }
        btnMonthFilter.setOnClickListener { changeButtonAppearance(btnMonthFilter) }
        btnWeekFilter.setOnClickListener { changeButtonAppearance(btnWeekFilter) }
        btnNeutralFilter.setOnClickListener { changeButtonAppearance(btnNeutralFilter) }
        btnRevenueFilter.setOnClickListener { changeButtonAppearance(btnRevenueFilter) }
        btnYearFilter.setOnClickListener { changeButtonAppearance(btnYearFilter) }
        btnSbmtFilter.setOnClickListener{
            viewModel.setFilterCursor(getAllItemsNeu())
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    private fun changeButtonAppearance(button:Button) {
        if(button.tag == "notPressed"){
            button.setBackgroundResource(R.drawable.rounded_select_button_pressed)
            button.setTextColor(Color.WHITE)
            button.tag = "pressed"
        }else{
            button.setBackgroundResource(R.drawable.rounded_select_button)
            button.setTextColor(ContextCompat.getColor(context!!,R.color.mainBlue))
            button.tag  ="notPressed"
        }
    }

    private fun setUpSeekbar(){
        sbAmountFilters.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                tvShowAmountFilter.text = "< $i"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(context!!,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(context!!,"stop tracking",Toast.LENGTH_SHORT).show()
                if(tvShowAmountFilter.text.toString()=="< 0"){
                    tvShowAmountFilter.text = "none"
                }
            }
        })
    }
}