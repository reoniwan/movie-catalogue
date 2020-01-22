package com.frozenproject.moviecatalogue.data.db

import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import java.util.concurrent.Executor

class LocalRepository(
    private val favoriteDao: MovieFavoriteDao,
    private var ioExecutor: Executor
) {

    fun getMoviesFavorite(): DataSource.Factory<Int, ResultMovie>{
        return favoriteDao.getAllFavoriteMovie("")
    }

    fun addToFavorite(data: ResultMovie){
        ioExecutor.execute {
            favoriteDao.insert(data)
        }
    }

    fun deleteFromFavorite(data: ResultMovie){
        ioExecutor.execute {
            favoriteDao.delete(data)
        }
    }
}