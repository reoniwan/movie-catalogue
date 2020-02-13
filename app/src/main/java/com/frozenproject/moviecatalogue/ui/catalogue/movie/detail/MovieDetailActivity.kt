package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, Injection.provideViewModelFactoryDetail(this, movieId))
            .get(MovieDetailViewModel::class.java)
    }

    companion object {
        var movieId: Int = 0
        const val EXTRA_MOVIE = "extra_movie"
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
        val data = intent.getParcelableExtra<ResultMovie>(EXTRA_MOVIE) //BUG API 29
        movieId = intent.getIntExtra(ID, 0)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)

        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_detail_movie.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_movie_detail.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

        toggle_favorite.apply {
            when (isFavorite) {
                true -> {
                    toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_red)
                    setOnClickListener {
                        viewModel.deleteFromFavorite(data) //BUG API 29
                        showToast("Delete Favorite!")
                    }
                }

                false -> {
                    toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
                    setOnClickListener {
                        viewModel.addToFavorite(data) //BUG API 29
                        showToast("Add to Favorite Success!")
                    }
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    private fun bindUI(it: MovieDetail) {
        txt_title_movie_detail.text = it.title
        date_movie.text = it.releaseDate
        txt_description.text = it.overview
        txt_rating_detail.text = it.rating.toString()

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        budget_movie.text = formatCurrency.format(it.budget)
        revenue_movie.text = formatCurrency.format(it.revenue)

        val moviePosterUrl = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(img_movie_detail)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}