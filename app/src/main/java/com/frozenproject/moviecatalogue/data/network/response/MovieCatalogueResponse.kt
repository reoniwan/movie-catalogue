package com.frozenproject.moviecatalogue.data.network.response

import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.google.gson.annotations.SerializedName

class MovieCatalogueResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultsMovie: List<ResultMovie>? = emptyList(),
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)