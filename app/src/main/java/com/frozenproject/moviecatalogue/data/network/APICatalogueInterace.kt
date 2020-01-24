package com.frozenproject.moviecatalogue.data.network

import com.frozenproject.moviecatalogue.BuildConfig
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import com.frozenproject.moviecatalogue.data.network.response.SeriesCatalogueResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APICatalogueInterface {

    @GET("discover/movie")
    fun getCatalogueMovie(
        @Query("api_key")apiKey: String? = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Call<MovieCatalogueResponse>

    @GET("discover/tv")
    fun getCatalogueSeries(
        @Query("page") page: Int
    ): Single<SeriesCatalogueResponse>

    @GET("tv/{tv_id}")
    fun getSeriesDetails(
        @Path("tv_id") id: Int
    ): Single<SeriesDetail>

    companion object{
        fun create(): APICatalogueInterface {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APICatalogueInterface::class.java)
        }
    }

}