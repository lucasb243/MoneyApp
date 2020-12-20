package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round


class AddItemFragment(var transactionDB: SQLiteDatabase, var adapter: TransactionAdapter):Fragment(R.layout.fragment_additem) {

    private lateinit var btnRevenue         :ImageButton
    private lateinit var btnExpense         :ImageButton
    private lateinit var btnNeutralExpense  :ImageButton
    private lateinit var etEtrAmount        :TextView
    private lateinit var etEtrDate          :TextView
    private lateinit var etEtrNote          :TextView
    private lateinit var tvErrorDesc        :TextView
    private lateinit var acTextView         :AutoCompleteTextView
    private lateinit var btnSbmt            :Button
    private var          type               :String     = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_additem, container, false)

        setUpViews(view)

        btnSbmt.setOnClickListener {
           if(checkOnClickSubmit()) {
                addTransactionItem(type, etEtrAmount.text.toString().toFloat(), acTextView.text.toString(), etEtrDate.text.toString(), etEtrNote.text.toString())
            }
        }
        setImgBtnOnClick()
        acTextView.setAdapter(ArrayAdapter<CategoryEnum>(activity!!, android.R.layout.simple_list_item_1, CategoryEnum.values()))
        return view
    }

    private fun addTransactionItem(type: String, amountNew: Float, category:String, createdAt: String, note:String?){
        if (note!=""){
            transactionDB.execSQL("INSERT INTO transactionList (type, amount, category, createdAt, note) VALUES ('${type}', ${amountNew}, '${category}', '${createdAt}', '${note}')")
            adapter.swapCursor(getAllItems())
        }else{
            transactionDB.execSQL("INSERT INTO transactionList (type, amount, category, createdAt) VALUES ('${type}', ${amountNew}, '${category}', '${createdAt}')")
            adapter.swapCursor(getAllItems())
        }

        activity!!.supportFragmentManager.popBackStack()
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
        btnRevenue          = view.findViewById(R.id.btnRevenue)
        btnExpense          = view.findViewById(R.id.btnExpense)
        btnNeutralExpense   = view.findViewById(R.id.btnNeutralExpense)
        etEtrAmount         = view.findViewById(R.id.etEnterAmount)
        //etEtrCtgry          = view.findViewById(R.id.etEnterCategory)
        etEtrDate           = view.findViewById(R.id.etEnterDate)
        etEtrDate.text      = getCurrentTime()
        etEtrNote           = view.findViewById(R.id.etEnterNote)
        btnSbmt             = view.findViewById(R.id.btnSbmt)
        acTextView          = view.findViewById(R.id.acTv)
        tvErrorDesc = view.findViewById(R.id.tvErrorDesciption)
    }

    private fun getCurrentTime():String{
        var date = LocalDateTime.now().toString()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm.ss.SSS")
        val formatted = date.format(formatter)
        //val finalDate = formatted.substring(0,10)+" "+formatted.substring(11, 16 )
        return formatted.substring(0, 10)+" "+formatted.substring(11, 16)
    }

    private fun limitDecimals(text:String):String{
        val format = DecimalFormat("##.##")
        return format.format(text)
    }

    private fun checkOnClickSubmit():Boolean{
        tvErrorDesc.text =""
        if(type !="empty") {
            if (CategoryEnum.values().any { it.name == acTextView.text.toString() }) {
                if (etEtrAmount.text.toString() != "" && etEtrDate.text.toString() != "") {
                    return true
                } else {
                    tvErrorDesc.text = "Error with amount or date!"
                }
            } else {
                tvErrorDesc.text = "Error with your category!"
            }
        }else{
            tvErrorDesc.text = "Please select a type first!"
        }
        return false
    }

    private fun setImgBtnOnClick(){
        btnRevenue.setOnClickListener {
            btnRevenue.setBackgroundResource(R.drawable.rounded_imagebutton_clicked)
            btnExpense.setBackgroundResource(R.drawable.rounded_imagebutton)
            btnNeutralExpense.setBackgroundResource(R.drawable.rounded_imagebutton)
            type = "r" // Revenue
        }
        btnExpense.setOnClickListener {
            btnExpense.setBackgroundResource(R.drawable.rounded_imagebutton_clicked)
            btnRevenue.setBackgroundResource(R.drawable.rounded_imagebutton)
            btnNeutralExpense.setBackgroundResource(R.drawable.rounded_imagebutton)
            type = "e" // Expense
        }
        btnNeutralExpense.setOnClickListener {
            btnNeutralExpense.setBackgroundResource(R.drawable.rounded_imagebutton_clicked)
            btnExpense.setBackgroundResource(R.drawable.rounded_imagebutton)
            btnRevenue.setBackgroundResource(R.drawable.rounded_imagebutton)
            type = "ne"
        }
    }

    /*private fun roundTwoDecimals(float: Float): Float {
        var num = float.toString()
        num.replace(",", ".")
        var num = String.format("%.2f", float)
        return num.toFloat()
    }*/
}