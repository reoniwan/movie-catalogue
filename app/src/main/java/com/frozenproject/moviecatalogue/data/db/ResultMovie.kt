package com.frozenproject.moviecatalogue.data.db

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResultMovie(
    @SerializedName("id")
    val idMovie: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val imageMovie: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("genre_ids")
    val genreMovie: List<Int>
)