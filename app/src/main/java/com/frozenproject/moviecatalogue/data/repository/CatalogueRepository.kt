package com.frozenproject.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.LocalRepository
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.source.TMDBDataSource
import com.frozenproject.moviecatalogue.data.network.source.RemoteRepository
import io.reactivex.disposables.CompositeDisposable

class CatalogueRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : TMDBDataSource {

    //Movie
    override fun getAllMovies(): LiveData<PagedList<ResultMovie>> {
        return remoteRepository.fetchLiveMoviePageList()
    }

    override fun searchCatalogueMovie(
        compositeDisposable: CompositeDisposable,
        movieTitle: String
    ): LiveData<PagedList<ResultMovie>> {
        return remoteRepository.searchCatalogueMovie(compositeDisposable, movieTitle)
    }

    override fun getNetworkStateSearchMovie(): LiveData<NetworkState> {
        return remoteRepository.getSearchNetworkStateMovie()
    }

    override fun getNetworkState(): LiveData<NetworkState> {
        return remoteRepository.getNetworkStateMovie()
    }

    override suspend fun getMovieDetails(movieId: Int): LiveData<ResultMovie> {
        return localRepository.getMovieDetails(movieId)
    }

    override suspend fun updateMovie(movie: ResultMovie) {
        return localRepository.updateMovie(movie)
    }

    //Series
    override fun getAllSeries(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultSeries>> {
        return remoteRepository.fetchLiveSeriesPageList(compositeDisposable)
    }

    override fun searchCatalogueSeries(
        compositeDisposable: CompositeDisposable,
        query: String
    ): LiveData<PagedList<ResultSeries>> {
        return remoteRepository.searchCatalogueSeries(compositeDisposable, query)
    }

    override fun getNetworkStateSearchSeries(): LiveData<NetworkState> {
        return remoteRepository.getSearchNetworkStateSeries()
    }

    override fun getNetworkStateSeries(): LiveData<NetworkState> {
        return remoteRepository.getNetworkStateSeries()
    }

    override suspend fun getSeriesDetails(seriesId: Int): LiveData<ResultSeries> {
        return localRepository.getSeriesDetails(seriesId)
    }

    override suspend fun updateSeries(series: ResultSeries) {
        return localRepository.updateSeries(series)
    }
}