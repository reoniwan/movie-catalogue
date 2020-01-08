package com.frozenproject.moviecatalogue.ui.catalogue.series.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.data.repository.SeriesCatalogueRepository
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description_series.*
import kotlinx.android.synthetic.main.layout_catalogue_detail_series.*

class DetailSeriesActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailSeriesViewModel
    private lateinit var seriesRepository: SeriesCatalogueRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail_series)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val seriesId: Int = intent.getIntExtra(ID, CATALOGUE_ID)

        val apiService: APICatalogueInterface = APICatalogueClient.getClient()

        seriesRepository = SeriesCatalogueRepository(apiService)

        viewModel = getViewModel(seriesId)

        viewModel.seriesDetails.observe(this, Observer { seriesEntry ->
            bindUI(seriesEntry)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_detail_series.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_series_detail.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun getViewModel(seriesId: Int): DetailSeriesViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailSeriesViewModel(seriesRepository, seriesId) as T
            }
        })[DetailSeriesViewModel::class.java]
    }

    private fun bindUI(seriesEntry: SeriesDetail?) {
        txt_title_series_detail.text = seriesEntry?.title
        txt_episode.text = seriesEntry?.episode.toString()
        txt_first_air_date.text = seriesEntry?.airDate
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