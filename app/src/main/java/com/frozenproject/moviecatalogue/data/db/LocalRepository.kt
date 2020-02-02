package com.frozenproject.moviecatalogue.data.db

import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import java.util.concurrent.Executor

class LocalRepository(
    private val favoriteDao: MovieFavoriteDao,
    private var ioExecutor: Executor,
    private val favoriteSeriesDao: SeriesFavoriteDao
) {

    //Favorite Movie
    fun getMoviesFavorite(): DataSource.Factory<Int, ResultMovie> {
        return favoriteDao.getAllFavoriteMovie()
    }

    fun addToFavorite(data: ResultMovie) {
        ioExecutor.execute {
            favoriteDao.insert(data)
        }
    }

    fun deleteFromFavorite(data: ResultMovie) {
        ioExecutor.execute {
            favoriteDao.delete(data)
        }
    }

    //Favorite Series
    fun getSeriesFavorite(): DataSource.Factory<Int, ResultSeries> {
        return favoriteSeriesDao.getAllFavoriteSeries()
    }

    fun addToFavoriteSeries(data: ResultSeries) {
        ioExecutor.execute {
            favoriteSeriesDao.insert(data)
        }
    }

    fun deleteFromFavoriteSeries(data: ResultSeries) {
        ioExecutor.execute {
            favoriteSeriesDao.delete(data)
        }
    }
}