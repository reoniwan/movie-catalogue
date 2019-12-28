package com.frozenproject.moviecatalogue.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.database.MovieCatalogueDao
import com.frozenproject.moviecatalogue.data.database.entity.ResultMovie
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.MovieSpesificCatalogueEntry
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.UnitSpecificMovieCatalogueEntry
import com.frozenproject.moviecatalogue.data.network.API_KEY
import com.frozenproject.moviecatalogue.data.network.CatalogueNetworkDataSource
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import com.google.gson.JsonObject
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class CatalogueRepositoryImpl(
    catalogueNetworkDataSource: CatalogueNetworkDataSource,
    private val movieCatalogueDao: MovieCatalogueDao
): CatalogueRepository{

    init {
        catalogueNetworkDataSource.apply {
            downloadedCatalogueMovie.observeForever{newCatalogueMovie ->
                persistFetchedCatalogueMovie(newCatalogueMovie)
            }
        }
    }



    private fun persistFetchedCatalogueMovie(fetchedMovie: MovieCatalogueResponse) {
        Thread.sleep(5000)
        GlobalScope.launch(Dispatchers.IO) {
            val movieCatalogueList = fetchedMovie.resultsMovie
            movieCatalogueDao.insert(movieCatalogueList)
        }
    }

    override suspend fun getMovieCatalogueList(): LiveData<List<MovieSpesificCatalogueEntry>> {
        val movie = movieCatalogueDao.getCatalogueResult()

       return withContext(Dispatchers.IO){
           initMovie()
           return@withContext movie
       }
    }


    private fun initMovie() {
         val listMovie = ArrayList<MovieSpesificCatalogueEntry>()

        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY"

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for(i in 0 until list.length()){
                        val movie = list.getJSONObject(i)
                        val movieItems = MovieSpesificCatalogueEntry()

                        movieItems.titleMovie = movie.getString("title")
                        movieItems.release = movie.getString("release_date")
                        movieItems.imageUrl = movie.getString("poster_path")
                        movieItems.rating = movie.getDouble("vote_average")

                        listMovie.add(movieItems)
                    }
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
                Log.d("onFailure", error.message.toString())
            }

        })
    }

}