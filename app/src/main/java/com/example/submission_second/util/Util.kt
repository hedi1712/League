package com.example.submission_second.util

import android.view.Gravity.apply
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.submission_second.R


@BindingAdapter("loadImageGlideUrl")
fun loadImageGlide(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        Glide.with(imageView.context).load(R.drawable.english_premier_league).into(imageView)
    } else {
        url.let {
            val imgUri = it.toUri().buildUpon().scheme("https").build()
            Glide.with(imageView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.english_premier_league)
                        .error(R.drawable.ic_error)
                )
                .into(imageView)
        }
    }
}