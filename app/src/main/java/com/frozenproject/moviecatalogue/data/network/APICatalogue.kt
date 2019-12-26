package com.frozenproject.moviecatalogue.data.network

import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.Deferred

const val API_KEY = "2b3907f8b4723221e1d39a76709e3269"
interface APICatalogue {


//    https://api.themoviedb.org/3/discover/movie?api_key=2b3907f8b4723221e1d39a76709e3269&language=en-US&page=1
    @GET("movie")
    fun getCatalogueMovie(
        @Query("language") languageCode: String = "en-US",
        @Query("page") page: Int
    ): Deferred<MovieCatalogueResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): APICatalogue{
            val requestInterceptor = Interceptor{chain->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor  chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/3/discover/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APICatalogue::class.java)
        }
    }
}