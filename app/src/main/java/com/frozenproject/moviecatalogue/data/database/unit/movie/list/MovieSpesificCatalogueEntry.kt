package com.frozenproject.moviecatalogue.data.database.unit.movie.list

import androidx.room.ColumnInfo

class MovieSpesificCatalogueEntry(

    @ColumnInfo(name = "title")
    var titleMovie: String? = "",
    @ColumnInfo(name = "vote_average")
    var rating: Double? = 0.0,
    @ColumnInfo(name = "release_date")
    var release: String? = "",
    @ColumnInfo(name = "poster_path")
    var imageUrl: String? = ""

)