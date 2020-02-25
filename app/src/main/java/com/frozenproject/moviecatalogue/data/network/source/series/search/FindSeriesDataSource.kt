package com.frozenproject.moviecatalogue.data.network.source.series.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.FIRST_PAGE
import com.frozenproject.moviecatalogue.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FindSeriesDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val apiService: APICatalogueInterface,
    private val query: String
) : PageKeyedDataSource<Int, ResultSeries>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultSeries>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.searchSeries(page, query)
                .subscribeOn(Schedulers.io())
                .subscribe({ searchSeries ->
                    callback.onResult(searchSeries.resultsSeries, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                }, { err ->
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("SeriesSearchDataSource", err.message!!)
                })
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultSeries>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.searchSeries(params.key, query)
                .subscribeOn(Schedulers.io())
                .subscribe({ searchSeries ->
                    callback.onResult(searchSeries.resultsSeries, params.key + 1)
                    networkState.postValue(NetworkState.LOADED)
                }, { err ->
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("SeriesSearchDataSource", err.message!!)
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultSeries>) {}
}