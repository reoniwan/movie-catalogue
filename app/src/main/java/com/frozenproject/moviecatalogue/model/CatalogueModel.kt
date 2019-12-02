package com.frozenproject.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatalogueModel(
    var name: String?,
    var rating: String?,
    var director: String?,
    var date: String?,
    var image: Int?,
    var genre: String?,
    var desc: String?
) : Parcelable