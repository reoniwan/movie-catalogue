package com.frozenproject.moviecatalogue.data.repository.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.LocalRepository
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.source.TMDBDataSource
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataSource
import com.frozenproject.moviecatalogue.data.repository.remote.MovieRemoteRepository
import io.reactivex.disposables.CompositeDisposable

class MovieCatalogueRepository(
    private val localRepository: LocalRepository,
    private val movieRemoteRepository: MovieRemoteRepository
) : TMDBDataSource {

    override fun getAllMovies(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultMovie>> {
        return movieRemoteRepository.fetchLiveMoviePageList(compositeDisposable)
    }

    override fun getDetailMovies(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetail> {
        return movieRemoteRepository.fetchMovieDetails(compositeDisposable, movieId)
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return movieRemoteRepository.getNetworkStateMovie()
    }

    override fun getNetworkStateDetail(): LiveData<NetworkState> {
        return movieRemoteRepository.getMovieDetailsNetworkState()
    }

    override fun getFavoriteMovies(id: Int): LiveData<MovieDetail> {
        return localRepository.getMoviesFavorite(id)
    }

    override fun unFavoriteMovie(data: MovieDetail) {
        return localRepository.deleteFromFavorite(data)
    }

    override fun addToFavorite(data: MovieDetail) {
        return localRepository.addToFavorite(data)
    }
}