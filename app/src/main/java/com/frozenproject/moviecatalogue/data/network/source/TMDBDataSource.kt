package com.frozenproject.moviecatalogue.data.network.source

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

interface TMDBDataSource {

    fun getAllMovies(): LiveData<PagedList<ResultMovie>>

    fun getFavoriteMovies(): DataSource.Factory<Int, ResultMovie>

    fun unFavoriteMovie(data: ResultMovie)

    fun addToFavorite(data: ResultMovie)
}