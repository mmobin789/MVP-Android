package com.example.myapplication.trending.network.images

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String?) {
    Glide.with(this).load(url).circleCrop().into(this)
}