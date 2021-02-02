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
    val dbHelper        = MutableLiveData<TransactionDBHelper>()

    fun setFilterCursor(filterItem: FilterItem) {
        filterCursor.value = createCursor(filterItem)
    }
    fun getFilterCursor():LiveData<Cursor>{
        filterCursor.value=getAllItemsNeu()
        return filterCursor
    }
    fun setDB(db:SQLiteDatabase){
        transactionDB.value= db
        filterCursor.value = getAllItems()
    }
    fun getDB():LiveData<SQLiteDatabase>{
        return transactionDB
    }
    fun setAdapter(adapter: TransactionAdapter){
        this.adapter.value=adapter
    }

    private fun getAllItemsNeu(): Cursor {
        val selArgs = arrayOf("r")
        return transactionDB.value!!.query(
                TransactionList.TransactionEntry.TABLE_NAME,
                null,
                "type = ?",
                selArgs,
                null,
                null,
                TransactionList.TransactionEntry.COLUMN_CREATEDAT + " DESC")
    }

    private fun getAllItems(): Cursor {
        return transactionDB.value!!.query(
                TransactionList.TransactionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TransactionList.TransactionEntry.COLUMN_CREATEDAT + " DESC")
    }

    private fun createCursor(filterItem: FilterItem):Cursor{
        var period ="-"+filterItem.timePeriod
        var c:Cursor = transactionDB.value!!.query(
                TransactionList.TransactionEntry.TABLE_NAME,
                null,
                "type = ? AND amount = ? AND createdAt > DATE(CURRENT_TIMESTAMP, '${period} days'",
                filterItem.getValuesArray(),
                null,
                null,
                TransactionList.TransactionEntry.COLUMN_CREATEDAT + " DESC")
        return c
    }
}