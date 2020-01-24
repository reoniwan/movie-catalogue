package com.frozenproject.moviecatalogue.data.network.source.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.FIRST_PAGE
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieCatalogueDataSource(
    private val apiService: APICatalogueInterface
) : PageKeyedDataSource<Int, ResultMovie>() {
    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)

            apiService.getCatalogueMovie(page = 1).enqueue(
                object : Callback<MovieCatalogueResponse>{
                    override fun onFailure(call: Call<MovieCatalogueResponse>?, t: Throwable) {
                        networkState.postValue(NetworkState.ERROR)
                    }

                    override fun onResponse(
                        call: Call<MovieCatalogueResponse>?,
                        response: Response<MovieCatalogueResponse>
                    ) {
                        if (response.isSuccessful){
                            val data = response.body()?.resultsMovie ?: emptyList()
                            callback.onResult(data, null, 2)
                        }else{
                            networkState.postValue(NetworkState.ERROR)
                        }
                    }

                }
            )



    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {
        networkState.postValue(NetworkState.LOADING)

            apiService.getCatalogueMovie(page = params.key).enqueue(
                object : Callback<MovieCatalogueResponse>{
                    override fun onFailure(call: Call<MovieCatalogueResponse>?, t: Throwable) {
                        networkState.postValue(NetworkState.ERROR)
                    }

                    override fun onResponse(
                        call: Call<MovieCatalogueResponse>,
                        response: Response<MovieCatalogueResponse>
                    ) {
                        if (response.isSuccessful){
                            val data = response.body()?.resultsMovie ?: emptyList()
                            callback.onResult(data, params.key + 1)
                        }else{
                            networkState.postValue(NetworkState.ERROR)
                        }
                    }

                }
            )


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovie>) {}

}