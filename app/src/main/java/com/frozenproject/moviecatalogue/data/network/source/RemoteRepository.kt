package com.frozenproject.moviecatalogue.data.network.source

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POST_PAGE
import com.frozenproject.moviecatalogue.data.network.source.movie.search.FindMovieDataSource
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataSource
import com.frozenproject.moviecatalogue.data.network.source.movie.search.FindMovieDataFactory
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesCatalogueDataSource
import com.frozenproject.moviecatalogue.data.network.source.series.search.FindSeriesDataFactory
import com.frozenproject.moviecatalogue.data.network.source.series.search.FindSeriesDataSource
import io.reactivex.disposables.CompositeDisposable

class RemoteRepository(
    val apiService: APICatalogueInterface
) : ProviderDataSource {
    //Variable Movie
    private lateinit var moviePage: LiveData<PagedList<ResultMovie>>
    private lateinit var moviesDataSourceFactory: MovieCatalogueDataFactory

    private lateinit var searchMovieDataFactory: FindMovieDataFactory
    //Variable Series
    private lateinit var seriesPage: LiveData<PagedList<ResultSeries>>
    private lateinit var seriesDataSourceFactory: SeriesCatalogueDataFactory

    private lateinit var searchSeriesDataFactory: FindSeriesDataFactory

    //Movie Repository
    fun fetchLiveMoviePageList(): LiveData<PagedList<ResultMovie>> {
        moviesDataSourceFactory =
            MovieCatalogueDataFactory(apiService)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        moviePage = LivePagedListBuilder(moviesDataSourceFactory, config)
            .build()

        return moviePage
    }

    private val _downloadMovieDetailsResponse = MutableLiveData<ResultMovie>()
    override val downloadMovieDetailsResponse: LiveData<ResultMovie>
        get() = _downloadMovieDetailsResponse

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    override suspend fun fetchMovieDetail(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            val fetchedMovieDetails = apiService.fetchMovieDetails(movieId)
            if (fetchedMovieDetails.isSuccessful) {
                _downloadMovieDetailsResponse.postValue(fetchedMovieDetails.body())
                _networkState.postValue(NetworkState.LOADED)
            } else {
                _networkState.postValue(NetworkState.ERROR)
                Log.e(TAG, fetchedMovieDetails.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getNetworkStateMovie(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieCatalogueDataSource, NetworkState>(
            moviesDataSourceFactory.movieLiveDataSource,
            MovieCatalogueDataSource::networkState
        )
    }

    fun searchCatalogueMovie(
        compositeDisposable: CompositeDisposable,
        movieTitle: String
    ): LiveData<PagedList<ResultMovie>> {
        searchMovieDataFactory =
            FindMovieDataFactory(
                compositeDisposable,
                apiService,
                movieTitle
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        moviePage = LivePagedListBuilder(searchMovieDataFactory, config)
            .build()

        return moviePage
    }

    fun getSearchNetworkStateMovie(): LiveData<NetworkState> {
        return Transformations.switchMap<FindMovieDataSource, NetworkState>(
            searchMovieDataFactory.searchDataSource,
            FindMovieDataSource::networkState
        )
    }


    //Series Repository
    fun fetchLiveSeriesPageList(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultSeries>> {
        seriesDataSourceFactory =
            SeriesCatalogueDataFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        seriesPage = LivePagedListBuilder(seriesDataSourceFactory, config)
            .build()

        return seriesPage
    }

    private val _downloadSeriesDetailsResponse = MutableLiveData<ResultSeries>()
    override val downloadSeriesDetailsResponse: LiveData<ResultSeries>
        get() = _downloadSeriesDetailsResponse

    private val _networkStateSeriesDetail = MutableLiveData<NetworkState>()
    val networkStateSeriesDetails: LiveData<NetworkState>
        get() = _networkStateSeriesDetail

    override suspend fun fetchSeriesDetail(seriesId: Int) {
        _networkStateSeriesDetail.postValue(NetworkState.LOADING)
        try {
            val fetchedSeriesDetails = apiService.fetchSeriesDetails(seriesId)
            if (fetchedSeriesDetails.isSuccessful) {
                _downloadSeriesDetailsResponse.postValue(fetchedSeriesDetails.body())
                _networkStateSeriesDetail.postValue(NetworkState.LOADED)
            } else {
                _networkStateSeriesDetail.postValue(NetworkState.ERROR)
                Log.e(TAG, fetchedSeriesDetails.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getNetworkStateSeries(): LiveData<NetworkState> {
        return Transformations.switchMap<SeriesCatalogueDataSource, NetworkState>(
            seriesDataSourceFactory.seriesLiveDataSource,
            SeriesCatalogueDataSource::networkState
        )
    }


    fun searchCatalogueSeries(
        compositeDisposable: CompositeDisposable,
        seriesTitle: String
    ): LiveData<PagedList<ResultSeries>> {
        searchSeriesDataFactory =
            FindSeriesDataFactory(
                compositeDisposable,
                apiService,
                seriesTitle
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        seriesPage = LivePagedListBuilder(searchSeriesDataFactory, config)
            .build()

        return seriesPage

    }

    fun getSearchNetworkStateSeries(): LiveData<NetworkState> {
        return Transformations.switchMap<FindSeriesDataSource, NetworkState>(
            searchSeriesDataFactory.searchDataSource,
            FindSeriesDataSource::networkState
        )
    }
}