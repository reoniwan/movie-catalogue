package com.frozenproject.moviecatalogue.data.network.source.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.FIRST_PAGE
import com.frozenproject.moviecatalogue.data.network.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieCatalogueDataSource(
    private val apiService: APICatalogueInterface
) : PageKeyedDataSource<Int, ResultMovie>() {
    private var page = FIRST_PAGE

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val fetchedMovies = apiService.getCatalogueMovie(page)
                if (fetchedMovies.isSuccessful) {
                    val response = fetchedMovies.body()!!
                    val listMovie = response.resultsMovie

                    callback.onResult(listMovie, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkState.postValue(NetworkState.ERROR)
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {
        networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val fetchedMovies = apiService.getCatalogueMovie(params.key)
                if (fetchedMovies.isSuccessful) {
                    val response = fetchedMovies.body()!!
                    val listMovie = response.resultsMovie

                    if (response.totalPages >= params.key) {
                        callback.onResult(listMovie, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        networkState.postValue(NetworkState.END_LIST)
                    }
                }
            } catch (e: Exception) {
                networkState.postValue(NetworkState.ERROR)
                e.printStackTrace()
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {}

}