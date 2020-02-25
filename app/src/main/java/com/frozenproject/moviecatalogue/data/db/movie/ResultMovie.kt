package com.frozenproject.moviecatalogue.data.db.movie

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

const val TABLE_MOVIE = "favorite_movie"
const val COLUMN_ID = "id"
const val COLUMN_TITLE = "title"
const val COLUMN_DATE = "release_date"
const val COLUMN_POSTER = "poster_path"
const val COLUMN_BACKDROP = "backdrop_path"
const val COLUMN_OVERVIEW = "overview"
const val COLUMN_BUDGET = "budget"
const val COLUMN_REVENUE = "revenue"
const val COLUMN_RATING = "vote_average"
const val COLUMN_IS_FAV = "is_favorite"

@Parcelize
@Entity(tableName = TABLE_MOVIE)
data class ResultMovie(
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID) var idMovie: Int,
    @SerializedName("overview")
    @ColumnInfo(name = COLUMN_OVERVIEW) var overview: String,
    @SerializedName("poster_path")
    @ColumnInfo(name = COLUMN_POSTER) var imageMovie: String,
    @SerializedName("release_date")
    @ColumnInfo(name = COLUMN_DATE) var releaseDate: String,
    @SerializedName("title")
    @ColumnInfo(name = COLUMN_TITLE) var title: String,
    @SerializedName("vote_average")
    @ColumnInfo(name = COLUMN_RATING) var rating: Double,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = COLUMN_BACKDROP) var backdrop: String,

    //Details
    @ColumnInfo(name = COLUMN_BUDGET) val budget: Int,
    @ColumnInfo(name = COLUMN_REVENUE) val revenue: Long,

    @ColumnInfo(name = COLUMN_IS_FAV) var isFavorite: Int
) : Parcelable