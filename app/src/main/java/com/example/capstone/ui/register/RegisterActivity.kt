package com.example.capstone.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.data.Result
import com.example.capstone.databinding.ActivityRegisterBinding
import com.example.capstone.ui.factory.ViewModelFactory
import com.example.capstone.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
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
        binding.registerButton.setOnClickListener {
            val name = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (password != confirmPassword) {
                binding.passwordEditText.error = "Password harus sama!"
                binding.confirmPasswordEditText.error = "Password harus sama!"
                return@setOnClickListener
            } else {
                binding.passwordEditText.error = null
                binding.confirmPasswordEditText.error = null
            }

            if (isInputValid(name, email, password)) {
                registerViewModel.register(name, email, password)
            }
        }

        observeViewModel()
        playAnimation()
    }

    private fun observeViewModel() {
        registerViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        registerViewModel.registerResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun isInputValid(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.usernameEditText.error = "Username tidak boleh kosong"
            isValid = false
        } else {
            binding.usernameEditText.error = null
        }

        if (email.isEmpty()) {
            binding.emailEditText.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Format email tidak valid"
            isValid = false
        } else {
            binding.emailEditText.error = null
        }

        if (password.isEmpty()) {
            binding.passwordEditText.error = "Password tidak boleh kosong"
            isValid = false
        } else if (password.length < 8) {
            binding.passwordEditText.error = "Password harus memiliki minimal 6 karakter"
            isValid = false
        } else {
            binding.passwordEditText.error = null
        }

        return isValid
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