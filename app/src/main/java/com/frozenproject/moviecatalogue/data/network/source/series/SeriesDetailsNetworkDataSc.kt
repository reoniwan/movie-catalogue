package com.frozenproject.moviecatalogue.data.network.source.series

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SeriesDetailsNetworkDataSc(
    private val apiService: APICatalogueInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedSeriesDetailsResponse = MutableLiveData<SeriesDetail>()
    val downloadedSeriesResponse: LiveData<SeriesDetail>
        get() = _downloadedSeriesDetailsResponse

    fun fetchSeriesDetails(seriesId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getSeriesDetails(seriesId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedSeriesDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("SeriesDetailsDataSource", it.message!!)
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("SeriesDetailsDataSource", e.message!!)
        }
    }
}