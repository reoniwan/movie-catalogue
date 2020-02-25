package com.frozenproject.moviefavoriteclient.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MovieData(
    val imageMovie: String?,
    val releaseDate: String?,
    val title: String?,
    val backdrop: String?
)