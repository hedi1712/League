package com.example.submission_second

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.submission_second.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_detail_league.*

class MainActivity : AppCompatActivity(), TempToolbarTitleListener {

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
        setupToolbar()
//        setupNavController()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    fun showToolbarBackArrow(shouldShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(shouldShow)
    }

//    private fun setupNavController() {
//        navController.addOnDestinationChangedListener(navigationListener)
//    }

    private fun hideToolbarSubtitle() {
        supportActionBar?.subtitle = null
    }

//    private val navigationListener =
//        NavController.OnDestinationChangedListener { _, destination, _ ->
//            invalidateOptionsMenu()
//            hideToolbarSubtitle()
//            when (destination.id) {
//                R.id.homeClub -> {
//                    showToolbar(true)
//                    showToolbarBackArrow(true)
//                }
//                R.id.detailLeagueFragment -> {
//                    showToolbar(true)
//                    showToolbarBackArrow(true)
//                }
//                R.id.searchView -> {
//                    showToolbar(true)
//                    showToolbarBackArrow(true)
//                }
//                else -> {
//                    showToolbar(false)
//                    showToolbarBackArrow(false)
//                }
//            }
//        }

    private fun showToolbar(shouldShow: Boolean) {
        if (shouldShow) toolbar.visibility = View.VISIBLE else toolbar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        when (navController.currentDestination?.id) {
            R.id.detailLeagueFragment -> navController.navigate(R.id.homeClub)
            else -> {
                navController.navigateUp()
            }
        }
        return true
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.detailLeagueFragment -> navController.navigate(R.id.homeClub)
            else -> {
                navController.navigateUp()
            }
        }
    }

    override fun updateTitle(title: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface TempToolbarTitleListener {
    fun updateTitle(title: String)
}
