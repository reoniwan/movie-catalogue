package com.frozenproject.moviecatalogue.data.db.series

import com.google.gson.annotations.SerializedName

data class ResultSeries(
    @SerializedName("id")
    val idSeries: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val imageSeries: String,
    @SerializedName("first_air_date")
    val releaseDate: String,
    @SerializedName("name")
    val titleSeries: String,
    @SerializedName("vote_average")
    val ratingSeries: Double
)