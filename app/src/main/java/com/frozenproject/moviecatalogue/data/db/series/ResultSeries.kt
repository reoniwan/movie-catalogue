package com.frozenproject.moviecatalogue.data.db.series

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_series")
data class ResultSeries(
    @SerializedName("id")
    @PrimaryKey
    var idSeries: Int,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var imageSeries: String,
    @SerializedName("first_air_date")
    var releaseDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("vote_average")
    var ratingSeries: Double,
    @SerializedName("backdrop_path")
    var backdropSeries: String
) : Parcelable