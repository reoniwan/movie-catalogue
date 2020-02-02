package com.frozenproject.moviecatalogue.data.db.series

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_series")
data class ResultSeries(
    @SerializedName("id")
    @PrimaryKey
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
    val ratingSeries: Double,
    @SerializedName("backdrop_path")
    val backdropSeries: String
) : Parcelable