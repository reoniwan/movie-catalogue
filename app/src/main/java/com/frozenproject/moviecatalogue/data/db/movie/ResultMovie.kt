package com.frozenproject.moviecatalogue.data.db.movie

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_movie")
data class ResultMovie(
    @SerializedName("id")
    @PrimaryKey
    var idMovie: Int,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var imageMovie: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("vote_average")
    var rating: Double,
    @SerializedName("vote_count")
    var vote: Int,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("backdrop_path")
    var backdrop: String
) : Parcelable{
}