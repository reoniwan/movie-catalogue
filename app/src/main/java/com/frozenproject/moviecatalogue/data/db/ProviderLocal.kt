package com.frozenproject.moviecatalogue.data.db

import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries

interface ProviderLocal {

    suspend fun getMovieDetails(movieId: Int): LiveData<ResultMovie>

    suspend fun updateMovie(movie: ResultMovie)

    suspend fun getSeriesDetails(seriesId: Int): LiveData<ResultSeries>

    suspend fun updateSeries(series: ResultSeries)

}