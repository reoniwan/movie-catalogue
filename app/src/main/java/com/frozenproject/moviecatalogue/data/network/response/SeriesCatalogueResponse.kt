package com.frozenproject.moviecatalogue.data.network.response

import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.google.gson.annotations.SerializedName

data class SeriesCatalogueResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultsSeries: List<ResultSeries>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
