package com.frozenproject.moviecatalogue.data.network.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

interface TMDBDataSource {

    //Movie
    fun getAllMovies(): LiveData<PagedList<ResultMovie>>

    fun searchCatalogueMovie(
        compositeDisposable: CompositeDisposable,
        movieTitle: String
    ): LiveData<PagedList<ResultMovie>>

    fun getNetworkStateSearchMovie(): LiveData<NetworkState>

    fun getNetworkState(): LiveData<NetworkState>

    suspend fun getMovieDetails(movieId: Int): LiveData<ResultMovie>

    suspend fun updateMovie(movie: ResultMovie)

    //Series
    fun getAllSeries(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultSeries>>

    fun searchCatalogueSeries(
        compositeDisposable: CompositeDisposable,
        query: String
    ): LiveData<PagedList<ResultSeries>>

    fun getNetworkStateSearchSeries(): LiveData<NetworkState>

    fun getNetworkStateSeries(): LiveData<NetworkState>

    suspend fun getSeriesDetails(seriesId: Int): LiveData<ResultSeries>

    suspend fun updateSeries(series: ResultSeries)
}