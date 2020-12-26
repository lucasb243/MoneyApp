package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment


class SetListFilters(var transactionDB: SQLiteDatabase, var adapter: TransactionAdapter):Fragment(R.layout.fragment_set_list_filters) {

    private lateinit var gridView           : GridView
    private lateinit var btnYearFilter      : Button
    private lateinit var btnMonthFilter     : Button
    private lateinit var btnWeekFilter      : Button
    private lateinit var btnRevenueFilter   : Button
    private lateinit var btnExpeneseFilter  : Button
    private lateinit var btnNeutralFilter   : Button
    private lateinit var tvShowAmountFilter : TextView
    private lateinit var sbAmountFilters    : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_list_filters, container, false)

        setUpViews(view)
        setUpButtonOnclick()

        val arrayAdapter: ArrayAdapter<CategoryEnum> = ArrayAdapter<CategoryEnum>(activity!!, android.R.layout.simple_list_item_multiple_choice, CategoryEnum.values())

        gridView.setAdapter(arrayAdapter)

        // When the user clicks on the GridItem

        // When the user clicks on the GridItem
        /*gridView.setOnItemClickListener(OnItemClickListener { a, v, position, id ->
            val o: Any = gridView.getItemAtPosition(position)
        })*/
        return view
    }

    private fun getAllItems(): Cursor {
        return transactionDB!!.query(
                TransactionList.TransactionEntry.TABLE_NAME,
                null,
                null,
                null,
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
    }

    private fun changeButtonAppearance(button:Button) {
        if(button.tag == "notPressed"){
            button.setBackgroundResource(R.drawable.rounded_select_button_pressed)
            button.setTextColor(Color.WHITE)
            button.tag = "pressed"
        }else{
            button.setBackgroundResource(R.drawable.rounded_select_button)
            button.setTextColor(R.color.mainBlue.toInt())
            button.tag  ="notPressed"
        }
    }

}