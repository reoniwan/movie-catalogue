package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.utils.Injection
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
            .get(MovieDetailViewModel::class.java)
    }

    companion object{
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
        val data = intent.getParcelableExtra<ResultMovie>(EXTRA_MOVIE)
        movieId = intent.getIntExtra(ID, CATALOGUE_ID)

        viewModel.setData(data)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)

        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_detail_movie.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_movie_detail.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

        toggle_favorite.setOnClickListener {
            if (isFavorite == readState()){
                toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_red)
                viewModel.deleteFromFavorite(data)
                saveStates(isFavorite)
                Toast.makeText(this, "Delete Favorite", Toast.LENGTH_SHORT).show()
            }else{
                toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
                viewModel.addToFavorite(data)
                saveStates(isFavorite)
                Toast.makeText(this, "Add Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveStates(favorite: Boolean) {
        val aSharedPreferences = this.getSharedPreferences(
            IS_FAVORITE, Context.MODE_PRIVATE
        )
        val aSharedEdit : SharedPreferences.Editor = aSharedPreferences.edit()

        aSharedEdit.putBoolean("State", favorite)
        aSharedEdit.apply()
    }

    private fun readState(): Boolean {

        val aSharedPreferences: SharedPreferences = this.getSharedPreferences(
            IS_FAVORITE, Context.MODE_PRIVATE
        )
        return aSharedPreferences.getBoolean("State", true)
    }

    private fun bindUI(it: MovieDetail) {
        txt_title_movie_detail.text = it.title
        date_movie.text = it.releaseDate
        txt_description.text = it.overview
        txt_rating_detail.text = it.rating.toString()

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        budget_movie.text = formatCurrency.format(it.budget)
        vote_movie.text = formatCurrency.format(it.revenue)

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