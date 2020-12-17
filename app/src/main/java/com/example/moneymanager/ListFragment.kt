package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment:Fragment(R.layout.fragment_list) {

    lateinit var transactionDB: SQLiteDatabase
    lateinit var adapter:TransactionAdapter
    private lateinit var filterBtn:ImageButton
    private lateinit var flActBtn:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = TransactionDBHelper(activity)
        transactionDB = dbHelper.writableDatabase
    }

    /*private fun addTransactionItem(){
        var a:Int = 1
        transactionDB.execSQL("INSERT INTO transactionList (type, amount, categorie, createdAt) VALUES('r', ${a}, 'test', CURRENT_TIMESTAMP)")
        adapter.swapCursor(getAllItems())
    }*/

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
        rvRecyclerView.addItemDecoration(MarginItemDecoration(10))

        flActBtn = view.findViewById(R.id.floatingActionButton)
        flActBtn.setOnClickListener {
            var ft:FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                ft.replace(R.id.flFrameLayout, AddItemFragment(transactionDB, adapter))
                ft.addToBackStack(null)
                ft.commit()
        }

        filterBtn=view.findViewById(R.id.ibFilterBtn)
        filterBtn.setOnClickListener {
            var ft:FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.flFrameLayout, SetListFilters(transactionDB, adapter))
            ft.addToBackStack(null)
            ft.commit()
        }
        return view
    }
}