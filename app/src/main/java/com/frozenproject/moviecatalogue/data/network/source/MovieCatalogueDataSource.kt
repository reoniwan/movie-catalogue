package com.frozenproject.moviecatalogue.data.network.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.frozenproject.moviecatalogue.data.db.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.FIRST_PAGE
import com.frozenproject.moviecatalogue.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieCatalogueDataSource(
    private val apiService: APICatalogueInterface,
    private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, ResultMovie>()
{
    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getCatalogueMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.resultsMovie, null, page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getCatalogueMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.resultsMovie, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else
                        {
                            networkState.postValue(NetworkState.END_LIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)

                    }
                )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {}

}