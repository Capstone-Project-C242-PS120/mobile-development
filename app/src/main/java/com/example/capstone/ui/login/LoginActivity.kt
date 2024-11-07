package com.example.capstone.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivityLoginBinding
import com.example.capstone.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signUpText.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        playAnimation()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.txtTitle, View.ALPHA, 1f).setDuration(700)
        val username = ObjectAnimator.ofFloat(binding.usernameEditText, View.ALPHA, 1f).setDuration(700)
        val password = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(700)
        val forgot = ObjectAnimator.ofFloat(binding.forgotPasswordText, View.ALPHA, 1f).setDuration(700)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(700)
        val or = ObjectAnimator.ofFloat(binding.txtOr, View.ALPHA, 1f).setDuration(700)
        val google = ObjectAnimator.ofFloat(binding.signInGoogle, View.ALPHA, 1f).setDuration(700)
        val sign = ObjectAnimator.ofFloat(binding.signUpText, View.ALPHA, 1f).setDuration(700)
        val img = ObjectAnimator.ofFloat(binding.imgLogin, View.ALPHA, 1f).setDuration(700)

        AnimatorSet().apply {
            playSequentially(title, username, password, forgot, login, or, google, sign, img)
            start()
        }
    }

}