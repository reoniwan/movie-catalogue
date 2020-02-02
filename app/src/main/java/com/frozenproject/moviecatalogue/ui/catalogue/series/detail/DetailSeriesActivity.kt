package com.frozenproject.moviecatalogue.ui.catalogue.series.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description_series.*
import kotlinx.android.synthetic.main.layout_catalogue_detail_series.*

class DetailSeriesActivity : AppCompatActivity() {

    private val viewModel: DetailSeriesViewModel by lazy {
        ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(DetailSeriesViewModel::class.java)
    }

    companion object {
        var seriesId = 0
        const val EXTRA_SERIES = "extra_series"
        const val IS_FAVORITE_SERIES = "favoriteSeries"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail_series)
        setSupportActionBar(toolbar)

        //Appbar
        val actionBar = supportActionBar
        actionBar?.title = ""

        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Data Series
        seriesId = intent.getIntExtra(ID, CATALOGUE_ID)
        val data = intent.getParcelableExtra<ResultSeries>(EXTRA_SERIES) //BUG API 29
        val isFavorite = intent.getBooleanExtra(
            IS_FAVORITE_SERIES,
            false
        )

        viewModel.seriesDetails.observe(this, Observer { seriesEntry ->
            bindUI(seriesEntry)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_detail_series.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_series_detail.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

        toggle_favorite_series.apply {
            when (isFavorite) {
                true -> {
                    toggle_favorite_series.setBackgroundResource(R.drawable.ic_favorite_red)
                    setOnClickListener {
                        viewModel.deleteFromFavorite(data) //BUG API 29
                        showToast("Delete Favorite!")
                    }
                }

                false -> {
                    toggle_favorite_series.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
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

    private fun bindUI(seriesEntry: SeriesDetail?) {
        txt_title_series_detail.text = seriesEntry?.title
        txt_episode_series.text = seriesEntry?.episode.toString()
        txt_first_air.text = seriesEntry?.airDate
        txt_last_air_date.text = seriesEntry?.lastAirDate
        txt_rating_detail_series.text = seriesEntry?.rating.toString()
        txt_description_series.text = seriesEntry?.overview

        val seriesPosterUrl = POSTER_BASE_URL + seriesEntry?.posterPath
        Glide.with(this)
            .load(seriesPosterUrl)
            .into(img_series_detail)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}