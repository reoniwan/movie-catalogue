package com.frozenproject.moviecatalogue.data.repository.favorite

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.LocalRepository
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.source.TMDBDataSource
import com.frozenproject.moviecatalogue.data.repository.remote.MovieRemoteRepository
import io.reactivex.disposables.CompositeDisposable

class MovieCatalogueRepository(
    private val localRepository: LocalRepository,
    private val movieRemoteRepository: MovieRemoteRepository
) : TMDBDataSource {

    override fun getAllMovies(): LiveData<PagedList<ResultMovie>> {
        return movieRemoteRepository.fetchLiveMoviePageList()
    }

    override fun getFavoriteMovies(): DataSource.Factory<Int, ResultMovie> {
        return localRepository.getMoviesFavorite()
    }

    override fun unFavoriteMovie(data: ResultMovie) {
        localRepository.deleteFromFavoriteMovie(data)
    }

    override fun addToFavorite(data: ResultMovie) {
        localRepository.addToFavoriteMovie(data)
    }
}