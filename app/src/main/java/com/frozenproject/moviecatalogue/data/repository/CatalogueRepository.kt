package com.frozenproject.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.LocalRepository
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.source.TMDBDataSource
import com.frozenproject.moviecatalogue.data.network.source.RemoteRepository
import io.reactivex.disposables.CompositeDisposable

class CatalogueRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : TMDBDataSource {

    //Movie
    override fun getAllMovies(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultMovie>> {
        return remoteRepository.fetchLiveMoviePageList(compositeDisposable)
    }

    override fun getDetailMovies(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetail> {
        return remoteRepository.fetchMovieDetails(compositeDisposable, movieId)
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

    override fun getNetworkStateDetail(): LiveData<NetworkState> {
        return remoteRepository.getMovieDetailsNetworkState()
    }

    override fun getFavoriteMovies(): DataSource.Factory<Int, ResultMovie> {
        return localRepository.getMoviesFavorite()
    }

    override fun unFavoriteMovie(data: ResultMovie) {
        return localRepository.deleteFromFavorite(data)
    }

    override fun addToFavorite(data: ResultMovie) {
        return localRepository.addToFavorite(data)
    }


    //Series
    override fun getAllSeries(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultSeries>> {
        return remoteRepository.fetchLiveSeriesPageList(compositeDisposable)
    }

    override fun getDetailSeries(
        compositeDisposable: CompositeDisposable,
        seriesId: Int
    ): LiveData<SeriesDetail> {
        return remoteRepository.fetchSeriesDetails(compositeDisposable, seriesId)
    }

    override fun searchCatalogueSeries(
        compositeDisposable: CompositeDisposable,
        seriesTitle: String
    ): LiveData<PagedList<ResultSeries>> {
        return remoteRepository.searchCatalogueSeries(compositeDisposable, seriesTitle)
    }

    override fun getNetworkStateSearchSeries(): LiveData<NetworkState> {
        return remoteRepository.getSearchNetworkStateSeries()
    }

    override fun getNetworkStateSeries(): LiveData<NetworkState> {
        return remoteRepository.getNetworkStateSeries()
    }

    override fun getNetworkStateDetailSeries(): LiveData<NetworkState> {
        return remoteRepository.getSeriesDetailsNetworkState()
    }

    override fun getFavoriteSeries(): DataSource.Factory<Int, ResultSeries> {
        return localRepository.getSeriesFavorite()
    }

    override fun unFavoriteSeries(data: ResultSeries) {
        return localRepository.deleteFromFavoriteSeries(data)
    }

    override fun addToFavoriteSeries(data: ResultSeries) {
        return localRepository.addToFavoriteSeries(data)
    }
}