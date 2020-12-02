package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment:Fragment(R.layout.fragment_list) {

    private lateinit var transactionDB: SQLiteDatabase
    private lateinit var adapter:TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = TransactionDBHelper(activity)
        transactionDB = dbHelper.writableDatabase
    }

    private fun addTransactionItem(){
        transactionDB.execSQL("INSERT INTO transactionList (type, amount, categorie, createdAt) VALUES('r', 10, 'test', CURRENT_TIMESTAMP)")
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val rvRecyclerView = view.findViewById<RecyclerView>(R.id.rvRecyclerView)
        rvRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = TransactionAdapter(activity!!, getAllItems())
        rvRecyclerView.adapter = this.adapter

        addTransactionItem() // inserting one row of test data

        return view
    }
}