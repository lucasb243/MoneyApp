package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ListFragment:Fragment(R.layout.fragment_list) {

    lateinit var transactionDB      : SQLiteDatabase
    lateinit var adapter            : TransactionAdapter
    private lateinit var filterBtn  : ImageButton
    private lateinit var flActBtn   : FloatingActionButton
    private lateinit var viewModel  : SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = TransactionDBHelper(activity)
        transactionDB = dbHelper.writableDatabase

        adapter = TransactionAdapter(activity!!, getAllItems())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel.getFilterCursor().observe(viewLifecycleOwner, androidx.lifecycle.Observer { cursor ->
            adapter.swapCursor(cursor)
        })
    }

    private fun addRandomTransactionItem() {
        var a = 1234.56
        for (i in 0..10) {
            if(i%2==0){
            transactionDB.execSQL("INSERT INTO transactionList (type, amount, category, createdAt) VALUES('r', ${a}, 'K', CURRENT_TIMESTAMP)")
            adapter.swapCursor(getAllItems())
            }else if(i%5==0){
                transactionDB.execSQL("INSERT INTO transactionList (type, amount, category, createdAt) VALUES('ne', ${a}, 'K', CURRENT_TIMESTAMP)")
                adapter.swapCursor(getAllItems())
            }else if(i%6==0){
                transactionDB.execSQL("INSERT INTO transactionList (type, amount, category, createdAt) VALUES('ne', ${a}, 'K', CURRENT_TIMESTAMP)")
                adapter.swapCursor(getAllItems())
            }else{
                transactionDB.execSQL("INSERT INTO transactionList (type, amount, category, createdAt) VALUES('e', ${a}, 'K', CURRENT_TIMESTAMP)")
                adapter.swapCursor(getAllItems())
            }
        }
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

//        if(mCursorFilter!=null){
//            adapter.swapCursor(mCursorFilter!!)
//        }
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
        addRandomTransactionItem()
        return view
    }
}