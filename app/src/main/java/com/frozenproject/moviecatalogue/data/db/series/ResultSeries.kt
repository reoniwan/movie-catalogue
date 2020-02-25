package com.frozenproject.moviecatalogue.data.db.series

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

const val TABLE_SERIES = "favorite_series"
const val COLUMN_ID_SERIES = "id"
const val COLUMN_TITLE_SERIES = "name"
const val COLUMN_POSTER_SERIES = "poster"
const val COLUMN_BACKDROP_SERIES = "backdrop"
const val COLUMN_RATING_SERIES = "rating"
const val COLUMN_DATE_FIRST = "first"
const val COLUMN_DATE_LAST = "last"
const val COLUMN_EPISODE = "episode"
const val COLUMN_OVERVIEW_SERIES = "overviewSeries"
const val COLUMN_FAV_SERIES = "isFavorite"

@Parcelize
@Entity(tableName = TABLE_SERIES)
data class ResultSeries(
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID_SERIES) var idSeries: Int,
    @SerializedName("overview")
    @ColumnInfo(name = COLUMN_OVERVIEW_SERIES) var overview: String,
    @SerializedName("poster_path")
    @ColumnInfo(name = COLUMN_POSTER_SERIES) var imageSeries: String,
    @SerializedName("first_air_date")
    @ColumnInfo(name = COLUMN_DATE_FIRST) var releaseDate: String,
    @SerializedName("name")
    @ColumnInfo(name = COLUMN_TITLE_SERIES) val name: String,
    @SerializedName("vote_average")
    @ColumnInfo(name = COLUMN_RATING_SERIES) var ratingSeries: Double,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = COLUMN_BACKDROP_SERIES) var backdropSeries: String,
    @SerializedName("number_of_episodes")
    @ColumnInfo(name = COLUMN_EPISODE) val episode: Int,
    @SerializedName("last_air_date")
    @ColumnInfo(name = COLUMN_DATE_LAST) val lastAirDate: String,
    @ColumnInfo(name = COLUMN_FAV_SERIES) var isFavorite: Int
) : Parcelable