package com.frozenproject.moviecatalogue.data.network

import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse

interface CatalogueNetworkDataSource {

    val downloadedCatalogueMovie: LiveData<MovieCatalogueResponse>

    suspend fun fetchCatalogueMovie(
        languageCode : String,
        page: Int
    )

}