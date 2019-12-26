package com.frozenproject.moviecatalogue.data.database.entity


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val MOVIE_ID = 0

@Entity(tableName = "MovieCatalogue", indices = [Index(value = ["id"])])
data class ResultMovie(
    @SerializedName("id")
    val idMovie: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = MOVIE_ID
}