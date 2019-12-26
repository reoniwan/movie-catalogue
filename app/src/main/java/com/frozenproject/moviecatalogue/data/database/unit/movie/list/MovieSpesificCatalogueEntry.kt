package com.frozenproject.moviecatalogue.data.database.unit.movie.list

import androidx.room.ColumnInfo

class MovieSpesificCatalogueEntry(

    @ColumnInfo(name = "title")
    override val titleMovie: String?,
    @ColumnInfo(name = "vote_average")
    override val rating: Double?,
    @ColumnInfo(name = "release_date")
    override val release: String?,
    @ColumnInfo(name = "poster_path")
    override val imageUrl: String?

):UnitSpecificMovieCatalogueEntry