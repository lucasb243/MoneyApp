package com.example.moneymanager

import android.provider.BaseColumns

class TransactionList {

    object TransactionEntry:BaseColumns{
        val TABLE_NAME      = "transactionList"
        val _ID             = "_id"
        val COLUMN_TYPE     = "type"
        val COLUMN_AMOUNT   = "amount"
        val COLUMN_CATEGORIE= "category"
        val COLUMN_NOTE     = "note"
        val COLUMN_CREATEDAT= "createdAt"
        val COLUMN_EDITEDAT = "editedAt"

    }
}