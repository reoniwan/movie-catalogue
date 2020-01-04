package com.frozenproject.moviecatalogue.data.network

import com.frozenproject.moviecatalogue.data.db.MovieDetail
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APICatalogueInterface {


    @GET("discover/movie?api_key=$API_KEY&language=US")
    fun getCatalogueMovie(
        @Query("page") page: Int
    ): Single<MovieCatalogueResponse>

    @GET("movie/{movie_id}?api_key=$API_KEY&language=US")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Single<MovieDetail>

}