package com.frozenproject.moviecatalogue.data.network.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POST_PAGE
import com.frozenproject.moviecatalogue.data.network.source.movie.search.FindMovieDataSource
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataSource
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieDetailsNetworkDataSc
import com.frozenproject.moviecatalogue.data.network.source.movie.search.FindMovieDataFactory
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesCatalogueDataSource
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesDetailsNetworkDataSc
import com.frozenproject.moviecatalogue.data.network.source.series.search.FindSeriesDataFactory
import com.frozenproject.moviecatalogue.data.network.source.series.search.FindSeriesDataSource
import io.reactivex.disposables.CompositeDisposable

class RemoteRepository(
    val apiService: APICatalogueInterface
) {
    //Variable Movie
    private lateinit var moviePage: LiveData<PagedList<ResultMovie>>
    private lateinit var moviesDataSourceFactory: MovieCatalogueDataFactory

    private lateinit var searchMovieDataFactory: FindMovieDataFactory
    //Variable Series
    private lateinit var seriesPage: LiveData<PagedList<ResultSeries>>
    private lateinit var seriesDataSourceFactory: SeriesCatalogueDataFactory

    private lateinit var searchSeriesDataFactory: FindSeriesDataFactory

    //Variable Movie Detail
    private lateinit var movieDetails: MovieDetailsNetworkDataSc
    //Variable Series Detail
    private lateinit var seriesDetails: SeriesDetailsNetworkDataSc

    //Movie Repository
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

    fun searchCatalogueMovie(
        compositeDisposable: CompositeDisposable,
        movieTitle: String
    ): LiveData<PagedList<ResultMovie>>{
        searchMovieDataFactory =
            FindMovieDataFactory(
                compositeDisposable,
                apiService,
                movieTitle)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        moviePage = LivePagedListBuilder(searchMovieDataFactory, config)
            .build()

        return moviePage
    }

    fun getSearchNetworkStateMovie(): LiveData<NetworkState>{
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

    fun fetchSeriesDetails(
        compositeDisposable: CompositeDisposable,
        seriesId: Int
    ): LiveData<SeriesDetail> {

        seriesDetails =
            SeriesDetailsNetworkDataSc(
                apiService,
                compositeDisposable
            )
        seriesDetails.fetchSeriesDetails(seriesId)

        return seriesDetails.downloadedSeriesResponse
    }

    fun getNetworkStateSeries(): LiveData<NetworkState> {
        return Transformations.switchMap<SeriesCatalogueDataSource, NetworkState>(
            seriesDataSourceFactory.seriesLiveDataSource,
            SeriesCatalogueDataSource::networkState
        )
    }

    fun getSeriesDetailsNetworkState(): LiveData<NetworkState> {
        return seriesDetails.networkState
    }

    fun searchCatalogueSeries(
        compositeDisposable: CompositeDisposable,
        seriesTitle: String
    ):LiveData<PagedList<ResultSeries>>{
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

    fun getSearchNetworkStateSeries(): LiveData<NetworkState>{
        return Transformations.switchMap<FindSeriesDataSource, NetworkState>(
            searchSeriesDataFactory.searchDataSource,
            FindSeriesDataSource::networkState
        )
    }
}