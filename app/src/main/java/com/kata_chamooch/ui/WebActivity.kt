package com.kata_chamooch.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.kata_chamooch.core.Constant
import com.kata_chamooch.databinding.ActivityWebBinding

private lateinit var binding: ActivityWebBinding
private var guideUrl: String? = null

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadFromIntent()
        loadWV()
    }

    private fun loadFromIntent() {
        guideUrl = intent.getStringExtra(Constant.KEY_GUIDE_URL)
        if (guideUrl == null) finish()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWV() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        binding.webView.loadUrl(guideUrl!!)
        binding.webView.settings.javaScriptEnabled = true
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        else
            super.onBackPressed()
    }
}