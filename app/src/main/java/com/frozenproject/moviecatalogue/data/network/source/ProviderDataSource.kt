package com.frozenproject.moviecatalogue.data.network.source

import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries

interface ProviderDataSource {

    val downloadMovieDetailsResponse: LiveData<ResultMovie>
    suspend fun fetchMovieDetail(movieId: Int)

    val downloadSeriesDetailsResponse: LiveData<ResultSeries>
    suspend fun fetchSeriesDetail(seriesId: Int)

}