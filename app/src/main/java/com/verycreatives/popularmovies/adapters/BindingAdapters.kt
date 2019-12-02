package com.verycreatives.popularmovies.adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.verycreatives.popularmovies.R
import com.verycreatives.popularmovies.network.RestApiClient.BASE_URL

const val IMG_BASE_URL="https://image.tmdb.org/t/p/w300"

@BindingAdapter("android:img")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url != null)
    Picasso.get().load(IMG_BASE_URL+url)
        //.resize(300,450)
        //.centerCrop()
        .error(R.drawable.ic_launcher_background)
        .into(imageView, object : Callback {
            override fun onSuccess() {
                //Log.d("ImageUrlBindingAdapter", "success")
            }

            override fun onError(e: Exception?) {
                Log.d("ImageUrlBindingAdapter", "error ${e?.message}")
            }
        })
    Log.d("heree", "link= $IMG_BASE_URL$url")
}