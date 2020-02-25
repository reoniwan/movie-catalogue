package com.frozenproject.moviecatalogue.data.db

import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.source.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocalRepository(
    private val favoriteDao: MovieFavoriteDao,
    private val favoriteSeriesDao: SeriesFavoriteDao,
    private val remoteRepository: RemoteRepository
) : ProviderLocal {

    companion object {
        private const val TYPE_MOVIE = "type_movie"
        private const val TYPE_SERIES = "type_series"
    }

    init {
        remoteRepository.apply {
            downloadMovieDetailsResponse.observeForever { movieDetails ->
                persistFetchedMovieDetails(movieDetails)
            }

            downloadSeriesDetailsResponse.observeForever { seriesDetails ->
                persistFetchedSeriesDetails(seriesDetails)
            }
        }
    }

    private fun persistFetchedSeriesDetails(seriesDetails: ResultSeries?) {
        GlobalScope.launch {
            favoriteSeriesDao.insert(seriesDetails!!)
        }
    }

    private fun persistFetchedMovieDetails(movieDetails: ResultMovie?) {
        GlobalScope.launch {
            favoriteDao.insert(movieDetails!!)
        }
    }

    //Favorite Movie
    override suspend fun getMovieDetails(movieId: Int): LiveData<ResultMovie> {
        return withContext(Dispatchers.IO) {
            initDetailsData(movieId, TYPE_MOVIE)
            return@withContext favoriteDao.getMovieById(movieId)
        }
    }

    override suspend fun updateMovie(movie: ResultMovie) {
        GlobalScope.launch {
            favoriteDao.update(movie)
        }
    }

    private suspend fun fetchMovieDetails(movieId: Int) {
        remoteRepository.fetchMovieDetail(movieId)
    }

    private fun isFetchMovieDetailsNeeded(movieId: Int): Boolean {
        val result = favoriteDao.countById(movieId)
        return result <= 0
    }

    //Favorite Series
    override suspend fun getSeriesDetails(seriesId: Int): LiveData<ResultSeries> {
        return withContext(Dispatchers.IO) {
            initDetailsData(seriesId, TYPE_SERIES)
            return@withContext favoriteSeriesDao.selectById(seriesId)
        }
    }

    private fun isFetchSeriesDetailsNeeded(seriesId: Int): Boolean {
        val result = favoriteSeriesDao.countById(seriesId)
        return result <= 0
    }

    private suspend fun fetchSeriesDetails(seriesId: Int) {
        remoteRepository.fetchSeriesDetail(seriesId)
    }

    override suspend fun updateSeries(series: ResultSeries) {
        GlobalScope.launch {
            favoriteSeriesDao.update(series)
        }
    }


    //InitialData
    private suspend fun initDetailsData(id: Int, typeMovie: String) {
        when (typeMovie) {
            TYPE_MOVIE -> {
                if (isFetchMovieDetailsNeeded(id)) {
                    fetchMovieDetails(id)
                }
            }

            TYPE_SERIES -> {
                if (isFetchSeriesDetailsNeeded(id)) {
                    fetchSeriesDetails(id)
                }
            }
        }
    }
}