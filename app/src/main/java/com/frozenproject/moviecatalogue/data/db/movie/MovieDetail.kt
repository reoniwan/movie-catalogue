package com.frozenproject.moviecatalogue.data.db.movie

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_movie")
data class MovieDetail(
    val budget: Int,
    @PrimaryKey
    @NonNull
    val id: Int?=0,
    @SerializedName("overview")
    val overview: String?="",
    @SerializedName("poster_path")
    val posterPath: String?="",
    @SerializedName("release_date")
    val releaseDate: String?="",
    val revenue: Long?=0,
    val title: String?="",
    @SerializedName("vote_average")
    val rating: Double?=0.0
): Parcelable