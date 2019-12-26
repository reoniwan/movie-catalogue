package com.frozenproject.moviecatalogue.data.network.response

import com.frozenproject.moviecatalogue.data.database.entity.ResultMovie
import com.google.gson.annotations.SerializedName

data class MovieCatalogueResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultsMovie: List<ResultMovie>
)