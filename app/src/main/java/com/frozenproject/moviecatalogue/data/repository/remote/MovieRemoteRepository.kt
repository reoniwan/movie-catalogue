package com.frozenproject.moviecatalogue.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POST_PAGE
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.movie.MovieCatalogueDataSource
import io.reactivex.disposables.CompositeDisposable

class MovieRemoteRepository(
    private val apiService: APICatalogueInterface
) {
    private lateinit var moviePage: LiveData<PagedList<ResultMovie>>
    private lateinit var moviesDataSourceFactory: MovieCatalogueDataFactory

    private val movieDataSourceFactory = MovieCatalogueDataFactory(apiService)

    private val config = PagedList.Config.Builder()
        .setPageSize(5)
        .setInitialLoadSizeHint(10)
        .setEnablePlaceholders(false)
        .build()


    fun fetchLiveMoviePageList(): LiveData<PagedList<ResultMovie>> {
        return LivePagedListBuilder(movieDataSourceFactory, config).build()
    }

}