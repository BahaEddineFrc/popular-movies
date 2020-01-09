package com.verycreatives.popularmovies.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.verycreatives.popularmovies.R
import com.verycreatives.popularmovies.databinding.ActivityWebsiteBinding
import com.verycreatives.popularmovies.viewmodels.WebViewModel

class Website : AppCompatActivity() {

    private lateinit var binding: ActivityWebsiteBinding
    private lateinit var model: WebViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_website)
        model = ViewModelProviders.of(this).get(WebViewModel::class.java)
        binding.viewModel = model


        intent.getStringExtra("homepageUrl")?.let {newsUrl->
            model.setWebviewUrl(newsUrl)
        }

        model.error.observe(this, androidx.lifecycle.Observer { isError->
            if(isError) {
                Snackbar.make(binding.root, "an error has occurred", Snackbar.LENGTH_LONG).show()
            }
        })
    }
}
