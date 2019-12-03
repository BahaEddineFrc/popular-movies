package com.verycreatives.popularmovies.adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.verycreatives.popularmovies.R
import jp.wasabeef.picasso.transformations.BlurTransformation

const val IMG_BASE_URL = "https://image.tmdb.org/t/p/w300"

@BindingAdapter("android:img")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url != null)
        Picasso.get().load(IMG_BASE_URL + url)
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
}

@BindingAdapter("android:blurImg")
fun setBlurImageUrl(imageView: ImageView, url: String?) {
    if (url != null)
        Picasso.get().load(IMG_BASE_URL + url)
            .transform(BlurTransformation(imageView.context, 25, 1))
            .error(R.drawable.ic_launcher_background)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    Log.d("ImageUrlBindingAdapter", "Blur error ${e?.message}")
                }
            })
    else
        imageView.setImageResource(R.drawable.gallery)
}