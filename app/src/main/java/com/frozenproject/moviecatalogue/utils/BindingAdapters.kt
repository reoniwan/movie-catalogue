package com.frozenproject.moviecatalogue.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso


@BindingAdapter("img_url")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/$imgUrl")
            .into(imgView)
    }
}