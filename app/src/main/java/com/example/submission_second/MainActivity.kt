package com.example.submission_second

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.submission_second.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), TempToolbarTitleListener {

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
        setupToolbar()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    fun showToolbarBackArrow(shouldShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(shouldShow)
    }


    private fun hideToolbarSubtitle() {
        supportActionBar?.subtitle = null
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(navigationListener)
    }

    private fun showBottomNavigation(shouldShow: Boolean) {
        if (shouldShow) bottomNavigationView.visibility =
            View.VISIBLE else bottomNavigationView.visibility = View.GONE
    }

    private val navigationListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu()
            hideToolbarSubtitle()
            when (destination.id) {
                R.id.splashScreenFragment -> {
                    showBottomNavigation(false)
                    showToolbar(false)
                    showToolbarBackArrow(false)
                }
                R.id.homeClub -> {
                    showBottomNavigation(true)
                    showToolbar(true)
                    showToolbarBackArrow(false)
                }
                R.id.detailLeagueFragment -> {
                    showBottomNavigation(true)
                    showToolbar(true)
                    showToolbarBackArrow(true)
                }
                R.id.searchView -> {
                    showBottomNavigation(true)
                    showToolbar(true)
                    showToolbarBackArrow(false)
                }
                R.id.favoriteFragment2 -> {
                    showBottomNavigation(true)
                    showToolbar(true)
                    showToolbarBackArrow(false)
                }
                R.id.detailMatchFragment -> {
                    showBottomNavigation(true)
                    showToolbar(true)
                    showToolbarBackArrow(true)
                }
                R.id.searchTeam -> {
                    showBottomNavigation(true)
                    showToolbar(true)
                    showToolbarBackArrow(false)
                }
                else -> {
                    showToolbar(false)
                    showToolbarBackArrow(false)
                }
            }
        }

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
            R.id.splashScreenFragment, R.id.favoriteFragment2 -> {
                binding.contentMain.bottomNavigationView.selectedItemId = R.id.homeClub
            }
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
