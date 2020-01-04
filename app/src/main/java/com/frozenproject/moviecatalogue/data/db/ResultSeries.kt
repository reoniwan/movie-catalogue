package com.frozenproject.moviecatalogue.data.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject


@Parcelize
data class ResultSeries(
    var dateSeries: String = "",
    var title: String = "",
    var overview: String = "",
    var vote_count: Int = 0,
    var imageSeries: String = "",
    var ratingSeries: Double = 0.0
):Parcelable