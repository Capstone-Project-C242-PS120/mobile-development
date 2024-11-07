package com.example.capstone.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.capstone.MainActivity
import com.example.capstone.R
import com.example.capstone.data.Result
import com.example.capstone.databinding.ActivityLoginBinding
import com.example.capstone.pref.SessionManager
import com.example.capstone.ui.factory.ViewModelFactory
import com.example.capstone.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
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

        sessionManager = SessionManager(this)

        binding.signUpText.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginButton.setOnClickListener {
            val email = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (isInputValid(email, password)) {
                loginViewModel.login(email, password)
            }
        }

        playAnimation()
        observeViewModel()
    }

    private fun observeViewModel() {
        loginViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        loginViewModel.loginResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    val token = result.data.data.accessToken
                    lifecycleScope.launch {
                        sessionManager.saveAuthToken(token)
                    }
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
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

    private fun isInputValid(email: String, password: String): Boolean {
        var isValid = true
        if (email.isEmpty()) {
            binding.usernameEditText.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.usernameEditText.error = "Format email tidak valid"
            isValid = false
        } else {
            binding.usernameEditText.error = null
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