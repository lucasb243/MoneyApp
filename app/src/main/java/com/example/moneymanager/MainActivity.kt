package com.example.moneymanager

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Dataset
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val listFragment = ListFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.pageHome -> setCurrentFragment(homeFragment)
                R.id.pageList -> setCurrentFragment(listFragment)
                R.id.pageProfile -> setCurrentFragment(profileFragment)
            }
            true
        }


        var moneyData:SQLiteDatabase = this.openOrCreateDatabase("moneyData", MODE_PRIVATE, null)
        moneyData.execSQL("CREATE TABLE IF NOT EXISTS transactions(id INTEGER PRIMARY KEY AUTOINCREMENT, type, amount, categorie, note, createdAt, editedAt)")

        
        for(i in 0..10){

            moneyData.execSQL("INSERT INTO transactions (type, amount, categorie, createdAt) VALUES('r', 10, 'test', CURRENT_TIMESTAMP)")
        }


        val rvRecylerView = findViewById<RecyclerView>(R.id.rvRecyclerView)

/*        val c:Cursor= moneyData.rawQuery("SELECT * FROM transactions", null)
        c.moveToFirst()
        var transactionData = ArrayList<String>()
*//*        do {
            var amnt = c.getString(c.getColumnIndex("amount"))
            var type1 = c.getString(c.getColumnIndex("type"))
            var id = c.getString(c.getColumnIndex("id"))
            var ergebnis = id+" "+amnt+" "+type1
            transactionData.add(ergebnis)
            c.moveToNext()
        }while(c!=null)*//*

        var transactionDataArray:Array<String> = transactionData.toTypedArray()
        val recyclerViewAdapter = DatabaseAdapter(transactionDataArray)
        //rvRecylerView.adapter = recyclerViewAdapter*/


    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFrameLayout, fragment)
            commit()
        }
    }
}