package com.frozenproject.moviefavoriteclient.model.constant

import android.net.Uri

object Constant {

    private const val TABLE_MOVIE = "favorite_movie"

    private const val AUTHORITY = "com.frozenproject.moviecatalogue.provider"
    val CONTENT_URI: Uri = Uri.Builder().scheme("content")
        .authority(AUTHORITY)
        .appendPath(TABLE_MOVIE)
        .build()

    const val POSTER_URL = "https://image.tmdb.org/t/p/w500/"
}