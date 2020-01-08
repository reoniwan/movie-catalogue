package com.frozenproject.moviecatalogue.data.db.movie

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val budget: Int,
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val title: String,
    @SerializedName("vote_average")
    val rating: Double
)