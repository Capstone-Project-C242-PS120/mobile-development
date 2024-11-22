package com.example.capstone.ui.subscriptionpage

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivitySubscriptionBinding
import com.example.capstone.ui.profile.ProfileFragment

class SubscriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.imgClose.setOnClickListener {
            navigateToProfile()
        }
    }

    private fun navigateToProfile() {
        val intent = Intent(this, ProfileFragment::class.java)
        startActivity(intent)
    }
}
