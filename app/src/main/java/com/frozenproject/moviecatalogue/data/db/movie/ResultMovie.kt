package com.frozenproject.moviecatalogue.data.db.movie

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "favorite_movie")
data class ResultMovie(
    @SerializedName("id")
    @PrimaryKey
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
    @SerializedName("vote_count")
    val vote: Int,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("backdrop_path")
    val backdrop: String
) : Parcelable