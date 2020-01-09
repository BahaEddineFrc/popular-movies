package com.verycreatives.popularmovies.viewmodels

import android.annotation.SuppressLint
import android.webkit.*
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel : ViewModel() {


    val url = ObservableField<String>()
    private var compt = 0

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    var hideProgress = ObservableField<Boolean>(false)

    fun setWebviewUrl(url: String) {
        //val securedUrl = url.substring(0, 4) + "s" + url.substring(4, url.length)
        this.url.set(url)
    }


    private inner class Client : WebViewClient() {
        override fun onReceivedError(
            view: WebView, request: WebResourceRequest, error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            hideProgress.set(true)
            _error.value = true
        }

        @SuppressLint("SetJavaScriptEnabled")
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            view.webChromeClient = WebChromeClient()
            view.settings.apply {
                allowFileAccess = true
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
                allowContentAccess = true
                javaScriptEnabled = true
                loadWithOverviewMode = true
                domStorageEnabled = true
            }


            if (compt < 1) {
                compt++
                view.reload()
            }

            hideProgress.set(true)
            _error.value = false
        }
    }

    fun getWebViewClient(): WebViewClient {
        return Client()
    }

}