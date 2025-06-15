package com.david.pczone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.david.pczone.API.TokenManager
import com.david.pczone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


       screenSplash.setKeepOnScreenCondition { false }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    safeNavigateTo(R.id.homeFragment)
                    true
                }
                R.id.nav_login -> {
                    val token = TokenManager.getToken(this)
                    if (token != null) {
                        safeNavigateTo(R.id.profilePageFragment)
                    } else {
                        safeNavigateTo(R.id.loginFragment2)
                    }
                    true
                }
                R.id.nav_cart -> {
                    safeNavigateTo(R.id.shoppingCartFragment)
                    true
                }
                R.id.nav_fav -> {
                    safeNavigateTo(R.id.favFragment)
                    true
                }
                R.id.nav_menu -> {
                    safeNavigateTo(R.id.shopMenuFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun safeNavigateTo(destinationId: Int) {
        val currentDestination = navController.currentDestination?.id
        if (currentDestination != destinationId) {
            navController.navigate(destinationId)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
