package com.example.moneymanager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.moneymanager.TransactionList.*

class TransactionDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        val DATABASE_NAME = "transactionlist.db"
        val DATABASE_VERSION = 1
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TransactionEntry.TABLE_NAME.toString() + " (" +
                TransactionEntry._ID.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TransactionEntry.COLUMN_TYPE.toString() + " TEXT NOT NULL, " +
                TransactionEntry.COLUMN_AMOUNT.toString() + " INTEGER NOT NULL, " +
                TransactionEntry.COLUMN_CATEGORIE.toString() +
                TransactionEntry.COLUMN_NOTE.toString() +
                TransactionEntry.COLUMN_CREATEDAT.toString() + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                TransactionEntry.COLUMN_EDITEDAT.toString() + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");"
        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE)
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     *
     *
     * The SQLite ALTER TABLE documentation can be found
     * [here](http://sqlite.org/lang_altertable.html). If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     *
     *
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     *
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME)
        onCreate(db)
    }
}