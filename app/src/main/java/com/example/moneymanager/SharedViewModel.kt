package com.example.moneymanager

import android.content.ClipData
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Filter

class SharedViewModel : ViewModel() {

    val filterCursor    = MutableLiveData<Cursor>()
    val transactionDB   = MutableLiveData<SQLiteDatabase>()
    val adapter         = MutableLiveData<TransactionAdapter>()

    fun setFilterCursor(cursor: Cursor) {
        filterCursor.value = cursor
    }
    fun getFilterCursor():LiveData<Cursor>{
        return filterCursor
    }
}