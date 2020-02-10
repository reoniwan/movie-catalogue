package com.frozenproject.moviecatalogue.data.network.source.movie.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.FIRST_PAGE
import com.frozenproject.moviecatalogue.data.network.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FindMovieDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val apiService: APICatalogueInterface,
    private val query: String
): PageKeyedDataSource<Int, ResultMovie>()  {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.searchMovie(page, query)
                .subscribeOn(Schedulers.io())
                .subscribe({searchMovie ->
                    callback.onResult(searchMovie.resultsMovie, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                },{error ->
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource", error.message!!)
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.searchMovie(params.key, query)
                .subscribeOn(Schedulers.io())
                .subscribe({searchMovie->
                    if (searchMovie.totalPages >= params.key) {
                        callback.onResult(searchMovie.resultsMovie, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        networkState.postValue(NetworkState.END_LIST)
                    }
                },{error->
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource", error.message!!)
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {}


}