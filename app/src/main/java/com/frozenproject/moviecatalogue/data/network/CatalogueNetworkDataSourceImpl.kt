package com.frozenproject.moviecatalogue.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import com.frozenproject.moviecatalogue.internal.NoConnectivityException


class CatalogueNetworkDataSourceImpl(
    private val apiCatalogue: APICatalogue
): CatalogueNetworkDataSource {

    companion object{
        const val PAGE_CATALOGUE = 1
    }

    private val _downloadedCatalogueMovie = MutableLiveData<MovieCatalogueResponse>()

    override val downloadedCatalogueMovie: LiveData<MovieCatalogueResponse>
        get() = _downloadedCatalogueMovie

    override suspend fun fetchCatalogueMovie(languageCode: String, page: Int) {
        try {
            val fetchedCatalogueMovie = apiCatalogue
                .getCatalogueMovie(languageCode, PAGE_CATALOGUE)
                .await()
            _downloadedCatalogueMovie.postValue(fetchedCatalogueMovie)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }
}