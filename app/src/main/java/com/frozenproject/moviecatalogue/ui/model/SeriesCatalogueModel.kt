package com.frozenproject.moviecatalogue.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SeriesCatalogueModel(
    var name: String?,
    var rating: String?,
    var studio: String?,
    var date: String?,
    var image: Int?,
    var genre: String?,
    var desc: String?
) : Parcelable