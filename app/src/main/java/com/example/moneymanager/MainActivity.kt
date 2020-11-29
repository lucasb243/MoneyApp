package com.example.moneymanager

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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
        moneyData.execSQL("CREATE TABLE IF NOT EXISTS revenues(type, amount, categorie, note, createdAt, editedAt)")
        moneyData.execSQL("CREATE TABLE IF NOT EXISTS expenses(type, amount, categorie, note, createdAt, editedAt)")

        for(i in 0..10){
            moneyData.execSQL("INSERT INTO revenues (type, amount, categorie, createdAt) VALUES('r', 10, 'test', CURRENT_TIMESTAMP)")
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFrameLayout, fragment)
            commit()
        }
    }
}