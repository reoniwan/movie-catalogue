package com.frozenproject.moviecatalogue.fragment.series

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.model.SeriesCatalogueModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description_series.*
import kotlinx.android.synthetic.main.layout_catalogue_detail_series.*

class CatalogueDetailSeries : AppCompatActivity() {

    companion object {
        const val DETAIL = "DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail_series)

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //DETAIL SERIES
        val seriesDetail: SeriesCatalogueModel? = intent.getParcelableExtra(DETAIL)

        txt_title_series_detail.text = seriesDetail?.name
        txt_detail_genre_series.text = seriesDetail?.genre
        txt_release_title_series.text = seriesDetail?.date
        txt_studio_name.text = seriesDetail?.studio
        txt_rating_detail_series.text = seriesDetail?.rating
        txt_description_series.text = seriesDetail?.desc

        Glide.with(this)
            .load(seriesDetail?.image)
            .into(img_series_detail)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
