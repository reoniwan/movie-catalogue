package com.frozenproject.moviecatalogue.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import java.util.concurrent.Executor

class LocalRepository(
    private val favoriteDao: MovieFavoriteDao,
    private var ioExecutor: Executor
) {

    fun getMoviesFavorite(id: Int): LiveData<MovieDetail>{
        return favoriteDao.getAllFavoriteMovie(id)
    }

    fun addToFavorite(data: MovieDetail){
        ioExecutor.execute {
            favoriteDao.insert(data)
        }
    }

    fun deleteFromFavorite(data: MovieDetail){
        ioExecutor.execute {
            favoriteDao.delete(data)
        }
    }
}