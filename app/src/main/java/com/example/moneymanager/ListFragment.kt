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
        val transactionDB = dbHelper.writableDatabase

        /*var recyclerView = view!!.findViewById<RecyclerView>(R.id.rvRecyclerView)//findViewById<RecyclerView>(R.id.rvRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)*/

      /*  val adapter = TransactionAdapter(activity!!, getAllItems())

        recyclerView.adapter = adapter*/
    }

    private fun addTransactionItem(){
        transactionDB.execSQL("INSERT INTO transactions (type, amount, categorie, createdAt) VALUES('r', 10, 'test', CURRENT_TIMESTAMP)")
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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.transaction_list_item, container, false)
        val rcyview = view.findViewById<RecyclerView>(R.id.rvRecyclerView)
        rcyview.layoutManager = LinearLayoutManager(activity)
        rcyview.adapter = TransactionAdapter(activity!!, getAllItems())


        return view
    }
}