package com.example.capstone.ui.register

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
import com.example.capstone.databinding.ActivityRegisterBinding
import com.example.capstone.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signInText.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        playAnimation()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.txtTittle, View.ALPHA, 1f).setDuration(700)
        val username = ObjectAnimator.ofFloat(binding.usernameEditText, View.ALPHA, 1f).setDuration(700)
        val email = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(700)
        val password = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(700)
        val confirmPassword = ObjectAnimator.ofFloat(binding.confirmPasswordEditText, View.ALPHA, 1f).setDuration(700)
        val registerButton = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(700)
        val txtOr = ObjectAnimator.ofFloat(binding.txtOr, View.ALPHA, 1f).setDuration(700)
        val signUpGoogle = ObjectAnimator.ofFloat(binding.signUpGoogle, View.ALPHA, 1f).setDuration(700)
        val signInText = ObjectAnimator.ofFloat(binding.signInText, View.ALPHA, 1f).setDuration(700)
        val imgRegister = ObjectAnimator.ofFloat(binding.imgRegister, View.ALPHA, 1f).setDuration(700)

        AnimatorSet().apply {
            playSequentially(title, username, email, password, confirmPassword, registerButton, txtOr, signUpGoogle, signInText, imgRegister)
            start()
        }
    }

}