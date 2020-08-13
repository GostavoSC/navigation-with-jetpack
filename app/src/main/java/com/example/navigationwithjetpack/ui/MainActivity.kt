package com.example.navigationwithjetpack.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.navigationwithjetpack.R
import com.example.navigationwithjetpack.ui.home_fragment.MainFragment
import com.example.navigationwithjetpack.ui.list_fragment.ListFragment
import com.example.navigationwithjetpack.ui.params_fragment.ParamsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNaviagtion()
        supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

    }

    private fun setupBottomNaviagtion() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_dest -> {
                    findNavController(R.id.nav_host).navigate(R.id.home_dest)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.list_dest -> {
                    findNavController(R.id.nav_host).navigate(R.id.list_dest)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.params_dest -> {
                    findNavController(R.id.nav_host).navigate(R.id.params_dest)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


}

