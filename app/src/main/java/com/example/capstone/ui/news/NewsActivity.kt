package com.example.capstone.ui.news

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val newsUrl = intent.getStringExtra("NEWS_URL")
        with(binding.webView) {
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    // Tampilkan ProgressBar saat loading
                    if (newProgress < 100) {
                        binding.progressBar.visibility = android.view.View.VISIBLE
                    } else {
                        binding.progressBar.visibility = android.view.View.GONE
                    }
                }
            }
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT

            if (newsUrl != null) {
                loadUrl(newsUrl)
            } else {
                Toast.makeText(this@NewsActivity, "Url tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}