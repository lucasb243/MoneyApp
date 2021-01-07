package com.example.moneymanager

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity() : AppCompatActivity() {


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
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFrameLayout, fragment)
            commit()
        }
    }
}