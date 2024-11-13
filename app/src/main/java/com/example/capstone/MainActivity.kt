package com.example.capstone

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.capstone.databinding.ActivityMainBinding
import com.example.capstone.pref.SessionManager
import com.example.capstone.ui.dashboard.FragmentDashboard
import com.example.capstone.ui.explore.ExploreFragment
import com.example.capstone.ui.history.HistoryFragment
import com.example.capstone.ui.profile.ProfileFragment
import com.example.capstone.ui.scan.ScanFragment
import com.example.capstone.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        checkAuthentication()
        val navBar = binding.bottomNavigationView

        if (savedInstanceState == null) {
            openFragment(FragmentDashboard())
        }

        navBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_dashboard -> {
                    openFragment(FragmentDashboard())
                    true
                }
                R.id.nav_explore -> {
                    openFragment(ExploreFragment())
                    true
                }
                R.id.nav_scan -> {
                    openFragment(ScanFragment())
                    true
                }
                R.id.nav_history -> {
                    openFragment(HistoryFragment())
                    true
                }
                R.id.nav_profile -> {
                    openFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkAuthentication() {
        val token = sessionManager.getAuthToken()
        if (token == null) {
            navigateToWelcome()
        }
    }

    private fun navigateToWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()

    }
}