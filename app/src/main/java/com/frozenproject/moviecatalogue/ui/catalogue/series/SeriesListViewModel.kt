package com.frozenproject.moviecatalogue.ui.catalogue.series

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.ResultSeries
import com.frozenproject.moviecatalogue.data.network.API_KEY
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class SeriesListViewModel:ViewModel() {

    val listSeries = MutableLiveData<ArrayList<ResultSeries>>()

    private var networkState: NetworkState? = null

    internal fun setSeries(page: Int){

        val client = AsyncHttpClient()
        val listItems = ArrayList<ResultSeries>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&page=$page&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()){
                        val seriesTv = list.getJSONObject(i)
                        val seriesTvItems = ResultSeries()

                        seriesTvItems.title = seriesTv.getString("name")
                        seriesTvItems.dateSeries = seriesTv.getString("first_air_date")
                        seriesTvItems.ratingSeries = seriesTv.getDouble("vote_average")
                        seriesTvItems.imageSeries = seriesTv.getString("poster_path")
                        seriesTvItems.overview = seriesTv.getString("overview")
                        seriesTvItems.vote_count = seriesTv.getInt("vote_count")

                        listItems.add(seriesTvItems)
                    }
                    listSeries.postValue(listItems)
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure",error.message.toString())
            }

        })

    }
    internal fun getSeries(): LiveData<ArrayList<ResultSeries>>{
        return listSeries
    }



}