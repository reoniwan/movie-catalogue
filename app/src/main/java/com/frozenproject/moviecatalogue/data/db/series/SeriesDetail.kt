package com.frozenproject.moviecatalogue.data.db.series

import com.google.gson.annotations.SerializedName

data class SeriesDetail(
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("first_air_date")
    val airDate: String,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("number_of_episodes")
    val episode: Int
)