package com.example.multi_pageapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.multi_pageapp.receivers.AirplaneModeReceiver

class MainActivity : AppCompatActivity() {
    private var flightModeReceiver: AirplaneModeReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        registerFlightModeReceiver()
    }

    override fun onPause() {
        super.onPause()
        unregisterFlightModeReceiver()
    }

    override fun onBackPressed() {
        handleBackNavigation()
    }

    private fun setupNavigation() {
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = hostFragment.navController

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom)
            insets
        }
    }

    private fun registerFlightModeReceiver() {
        flightModeReceiver = AirplaneModeReceiver()
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(flightModeReceiver, intentFilter)
    }

    private fun unregisterFlightModeReceiver() {
        flightModeReceiver?.let { receiver ->
            unregisterReceiver(receiver)
            flightModeReceiver = null
        }
    }

    private fun handleBackNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination?.id == R.id.mainFragment) {
            super.onBackPressed()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}

