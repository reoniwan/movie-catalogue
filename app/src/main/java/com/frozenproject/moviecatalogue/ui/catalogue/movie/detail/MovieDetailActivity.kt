package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.MovieDetail
import com.frozenproject.moviecatalogue.data.network.APICatalogueClient
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.frozenproject.moviecatalogue.data.repository.MovieCatalogueRepository
import kotlinx.android.synthetic.main.layout_catalogue_description.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movieRepository: MovieCatalogueRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: APICatalogueInterface = APICatalogueClient.getClient()

        movieRepository = MovieCatalogueRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_detail_movie.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_movie_detail.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun getViewModel(movieId: Int): MovieDetailViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T:ViewModel?>create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieRepository, movieId) as T
            }
        })[MovieDetailViewModel::class.java]
    }

    private fun bindUI(it: MovieDetail?) {
        txt_title_movie_detail.text = it?.title
        date_movie.text = it?.releaseDate
        txt_description.text = it?.overview
        txt_rating_detail.text = it?.rating.toString()

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        budget_movie.text = formatCurrency.format(it?.budget)
        revenue_movie.text = formatCurrency.format(it?.revenue)

        val moviePosterUrl = POSTER_BASE_URL + it?.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(img_movie_detail)

    }
}