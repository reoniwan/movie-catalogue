package com.frozenproject.moviecatalogue.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POST_PAGE
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataSource
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieDetailsNetworkDataSc
import io.reactivex.disposables.CompositeDisposable

class MovieRemoteRepository(
    val apiService: APICatalogueInterface
) {
    private lateinit var moviePage: LiveData<PagedList<ResultMovie>>
    private lateinit var moviesDataSourceFactory: MovieCatalogueDataFactory

    private lateinit var movieDetails: MovieDetailsNetworkDataSc

    fun fetchLiveMoviePageList(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultMovie>> {
        moviesDataSourceFactory =
            MovieCatalogueDataFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        moviePage = LivePagedListBuilder(moviesDataSourceFactory, config)
            .build()

        return moviePage
    }

    fun fetchMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetail> {

        movieDetails =
            MovieDetailsNetworkDataSc(
                apiService,
                compositeDisposable
            )
        movieDetails.fetchMovieDetails(movieId)

        return movieDetails.downloadedMovieResponse
    }

    fun getNetworkStateMovie(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieCatalogueDataSource, NetworkState>(
            moviesDataSourceFactory.movieLiveDataSource,
            MovieCatalogueDataSource::networkState
        )
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetails.networkState
    }
}