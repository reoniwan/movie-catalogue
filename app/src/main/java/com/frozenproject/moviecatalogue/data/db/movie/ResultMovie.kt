package com.frozenproject.moviecatalogue.data.db.movie

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "favorite_movie")
data class ResultMovie(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    val idMovie: Int? = 0,
    @SerializedName("overview")
    val overview: String? = "",
    @SerializedName("poster_path")
    val imageMovie: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("vote_average")
    val rating: Double? = 0.0,
    @SerializedName("vote_count")
    val vote: Int? = 0,
    @SerializedName("popularity")
    val popularity: Double? = 0.0,
    @SerializedName("backdrop_path")
    val backdrop_path: String? = ""
):Parcelable