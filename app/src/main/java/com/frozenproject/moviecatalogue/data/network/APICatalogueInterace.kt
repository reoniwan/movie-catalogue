package com.frozenproject.moviecatalogue.data.network

import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import com.frozenproject.moviecatalogue.data.network.response.SeriesCatalogueResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APICatalogueInterface {

    @GET("discover/movie")
    fun getCatalogueMovie(
        @Query("page") page: Int
    ): Single<MovieCatalogueResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Single<MovieDetail>

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
    fun getSeriesDetails(
        @Path("tv_id") id: Int
    ): Single<SeriesDetail>

    @GET("search/tv")
    fun searchSeries(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Single<SeriesCatalogueResponse>



}