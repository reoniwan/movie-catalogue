package com.frozenproject.moviecatalogue.ui.catalogue.series

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.ResultSeries
import kotlinx.android.synthetic.main.item_list_catalogue_series.*
import kotlinx.android.synthetic.main.layout_catalogue_description_series.*
import kotlinx.android.synthetic.main.layout_catalogue_detail_series.*

class DetailSeriesActivity: AppCompatActivity() {

        companion object{
            const val DETAIL_SERIES = "detail"
        }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.layout_catalogue_detail_series)

        val seriesDetail: ResultSeries? = intent.getParcelableExtra(DETAIL_SERIES)

        txt_title_series_detail.text = seriesDetail?.title

        date_series.text = seriesDetail?.dateSeries

        txt_description_series.text = seriesDetail?.overview

        txt_rating_series.text = seriesDetail?.ratingSeries.toString()

        txt_studio_name.text = seriesDetail?.vote_count.toString()

        Glide.with(this)
            .load(seriesDetail?.imageSeries)
            .into(img_series_detail)
    }

}