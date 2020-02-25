package com.frozenproject.moviecatalogue.ui.catalogue.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.utils.CoroutineScopedActivity
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : CoroutineScopedActivity() {

    private lateinit var viewModel: MovieDetailViewModel

    companion object {
        const val IS_FAVORITE = "favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail)

        //Appbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""

        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Data Movie
        val isFavorite = intent.getBooleanExtra(IS_FAVORITE, false)
        val movieId = intent.getIntExtra(ID, 0)

        viewModel =
            ViewModelProvider(this, Injection.provideViewModelFactoryDetail(this, movieId))
                .get(MovieDetailViewModel::class.java)

        if (isFavorite) {
            bindDetailsMovie()
        } else {
            bindDetailsSeries()
        }


    }


    private fun bindDetailsMovie() = launch {
        val movie = viewModel.movieDetails.await()

        movie.observe(this@MovieDetailActivity, Observer { movies ->
            if (movies == null) return@Observer

            //Update Data
            txt_title_movie_detail.text = movies.title
            date_movie.text = movies.releaseDate
            txt_description.text = movies.overview
            txt_rating_detail.text = movies.rating.toString()


            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            budget_movie.text = formatCurrency.format(movies.budget)
            revenue_movie.text = formatCurrency.format(movies.revenue)

            val poster = POSTER_BASE_URL + movies.imageMovie

            Glide.with(applicationContext)
                .load(poster)
                .into(img_movie_detail)

            changeDataFav(movies.isFavorite)

            //Favorite
            toggle_favorite.setOnClickListener {
                updateFav(movies, null)
            }

            //Hide ProgressBar
            progress_bar_detail_movie.visibility = View.GONE

        })
    }

    private fun bindDetailsSeries() = launch {
        val series = viewModel.seriesDetails.await()

        series.observe(this@MovieDetailActivity, Observer { seriesEntry ->
            if (seriesEntry == null) return@Observer

            //UpdateData
            txt_title_movie_detail.text = seriesEntry.name
            budget_movie.text = seriesEntry.episode.toString()
            revenue_movie.text = seriesEntry.releaseDate
            date_movie.text = seriesEntry.lastAirDate
            txt_rating_detail.text = seriesEntry.ratingSeries.toString()
            txt_description.text = seriesEntry.overview

            val seriesPosterUrl = POSTER_BASE_URL + seriesEntry.imageSeries
            Glide.with(applicationContext)
                .load(seriesPosterUrl)
                .into(img_movie_detail)

            changeDataFav(seriesEntry.isFavorite)

            //Favorite
            toggle_favorite.setOnClickListener {
                updateFav(null, seriesEntry)
            }

            //Change Title For Series
            val episode = getString(R.string.total_episodes)
            budget.text = episode

            val firstDate = getString(R.string.date)
            revenue.text = firstDate

            val lastDate = getString(R.string.date_last)
            date.text = lastDate

            //Hide ProgressBar
            progress_bar_detail_movie.visibility = View.GONE
        })
    }

    private fun changeDataFav(favorite: Int) {
        val txtFav: String =
            if (favorite == 1) {
                toggle_favorite.setImageResource(R.drawable.ic_favorite_red)
                getString(R.string.unlike)
            } else {
                toggle_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                getString(R.string.like)
            }

        txt_like.text = txtFav
    }

    private fun updateFav(movie: ResultMovie?, series: ResultSeries?) = launch {
        if (movie != null) {
            when (movie.isFavorite) {
                0 -> {
                    movie.isFavorite = 1
                    toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_red)
                    showToast("Add to Favorite Success!!")
                }

                1 -> {
                    movie.isFavorite = 0
                    toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
                    showToast("Delete Favorite!!")
                }
            }

            viewModel.addMovieFavorite(movie)
        } else if (series != null) {
            when (series.isFavorite) {
                0 -> {
                    series.isFavorite = 1
                    toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_red)
                    showToast("Add to Favorite Success!!")
                }

                1 -> {
                    series.isFavorite = 0
                    toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
                    showToast("Delete Favorite!!")
                }
            }

            viewModel.addSeriesFavorite(series)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}