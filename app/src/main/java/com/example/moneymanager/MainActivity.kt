package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Dataset
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

//    private var transactionDB:SQLiteDatabase? = null
//    private var adapter:TransactionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val listFragment = ListFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            var ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.pageHome -> ft.replace(R.id.flFrameLayout, homeFragment) // setCurrentFragment(homeFragment)
                R.id.pageList -> ft.replace(R.id.flFrameLayout, listFragment) //setCurrentFragment(listFragment)
                R.id.pageProfile -> ft.replace(R.id.flFrameLayout, profileFragment) //setCurrentFragment(profileFragment)
            }
           // ft.replace(R.id.flFrameLayout, AddItemFragment())
            ft.addToBackStack(null)
            ft.commit()
            true
        }


        /*val dbHelper = TransactionDBHelper(this)
        val transactionDB = dbHelper.writableDatabase

        var recyclerView = findViewById<RecyclerView>(R.id.rvRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = TransactionAdapter(this, getAllItems())

        recyclerView.adapter = adapter*/
    }

    /*private fun addTransactionItem(){
        transactionDB!!.execSQL("INSERT INTO transactions (type, amount, categorie, createdAt) VALUES('r', 10, 'test', CURRENT_TIMESTAMP)")
        adapter!!.swapCursor(getAllItems())
    }*/

    /*private fun getAllItems():Cursor {
        return transactionDB!!.query(
                TransactionList.TransactionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
        TransactionList.TransactionEntry.COLUMN_CREATEDAT + " DESC")
    }*/

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFrameLayout, fragment)
            commit()
        }
    }
}