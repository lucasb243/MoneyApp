package com.example.moneymanager

import android.database.Cursor

interface Communicator {
    fun passDataCom(cursor: Cursor)
}