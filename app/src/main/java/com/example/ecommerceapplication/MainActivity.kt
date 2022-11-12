package com.example.ecommerceapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.ecommerceapplication.databinding.ActivityMainBinding
import com.example.ecommerceapplication.extensions.createNotificationChannels
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        createNotificationChannels(this)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_user, R.id.navigation_cart)
        )

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (item.isChecked) {
                        NavigationUI.onNavDestinationSelected(item, navController)
                    } else {
                        item.isChecked = true
                        navController.popBackStack(R.id.navigation_home, false)
                        return@setOnItemSelectedListener false
                    }
                }
                else ->
                    NavigationUI.onNavDestinationSelected(item, navController)
            }
            true
        }

        // Setup action bar with nav controller to implement up button in action bar
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     * When up button is clicked on a nav_fragment
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_fragment)
        return navController.navigateUp()
    }
}