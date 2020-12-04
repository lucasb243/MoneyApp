package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class AddItemFragment(var transactionDB:SQLiteDatabase, var adapter: TransactionAdapter):Fragment(R.layout.fragment_additem) {

    private lateinit var btnRevenue     :ImageButton
    private lateinit var btnExpense     :ImageButton
    private lateinit var btnNeutral     :ImageButton
    private lateinit var tvEtrAmount    :TextView
    private lateinit var tvEtrDate      :TextView
    private lateinit var tvEtrCtgry     :TextView
    private lateinit var tvEtrNote      :TextView
    private lateinit var btnSbmt        :Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        adapter = TransactionAdapter(activity!!,getAllItems())
        val dbHelper = TransactionDBHelper(activity)
        transactionDB = dbHelper.writableDatabase
        setUpViews()
    }

    private fun addTransactionItem(){
        var a:Int = 1
        Log.i("test", "test")
        transactionDB.execSQL("INSERT INTO transactionList (type, amount, categorie, createdAt) VALUES('r', ${a}, 'test', CURRENT_TIMESTAMP)")
        adapter.swapCursor(getAllItems())
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

    private fun setUpViews(){
        btnRevenue  = activity!!.findViewById(R.id.btnRevenue)
        btnExpense  = activity!!.findViewById(R.id.btnExpense)
        btnNeutral  = activity!!.findViewById(R.id.btnNeutralExpense)
        tvEtrAmount = activity!!.findViewById(R.id.tvEnterAmount)
        tvEtrCtgry  = activity!!.findViewById(R.id.tvEnterCategory)
        tvEtrDate   = activity!!.findViewById(R.id.tvEnterPayDate)
        tvEtrNote   = activity!!.findViewById(R.id.tvEnterNote)
        btnSbmt     = activity!!.findViewById(R.id.btnSbmt)
        btnSbmt.setOnClickListener {
            addTransactionItem()
        }
    }
}