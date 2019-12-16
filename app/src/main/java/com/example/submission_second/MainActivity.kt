package com.example.submission_second

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.submission_second.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),TempToolbarTitleListener {
    override fun updateTitle(title: String) {
        binding.mainToolbar.title = title
    }

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
        setupToolbar()

    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    fun showToolbarBackArrow(shouldShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(shouldShow)
    }

    private fun showToolbar(shouldShow: Boolean) {
        if (shouldShow) toolbar.visibility = View.VISIBLE else toolbar.visibility = View.GONE
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            else -> {
                navController.navigateUp()
            }
        }
    }
}

interface TempToolbarTitleListener {
    fun updateTitle(title: String)
}
