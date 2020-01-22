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
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail)
        //Appbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""

        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Data Movie
        val movieId: Int = intent.getIntExtra(ID, CATALOGUE_ID)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
            .get(MovieDetailViewModel::class.java)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_detail_movie.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_movie_detail.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}