package com.frozenproject.moviecatalogue.data.network

import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import com.frozenproject.moviecatalogue.data.network.response.SeriesCatalogueResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APICatalogueInterface {

    @GET("discover/movie")
    suspend fun getCatalogueMovie(
        @Query("page") page: Int
    ): Response<MovieCatalogueResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") id: Int
    ): Response<ResultMovie>

    @GET("search/movie")
    fun searchMovie(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Single<MovieCatalogueResponse>

    @GET("discover/movie")
    fun getMovieRelease(
        @Query("primary_release_date.gte") releaseDate: String,
        @Query("primary_release_date.lte") reReleaseDate: String
    ): Call<MovieCatalogueResponse>

    @GET("discover/tv")
    fun getCatalogueSeries(
        @Query("page") page: Int
    ): Single<SeriesCatalogueResponse>

    @GET("tv/{tv_id}")
    suspend fun fetchSeriesDetails(
        @Path("tv_id") id: Int
    ): Response<ResultSeries>

    @GET("search/tv")
    fun searchSeries(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Single<SeriesCatalogueResponse>

}