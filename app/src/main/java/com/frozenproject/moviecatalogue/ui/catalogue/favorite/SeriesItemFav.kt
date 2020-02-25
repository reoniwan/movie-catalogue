package com.frozenproject.moviecatalogue.ui.catalogue.favorite

import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_favourite_series.*

class SeriesItemFav(
    val series: ResultSeries
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            bindUI()
        }
    }

    private fun ViewHolder.bindUI() {
        title_favourite_series.text = series.name
        txt_release_favourite_series.text = series.releaseDate

        val poster = POSTER_BASE_URL + series.imageSeries
        val backDrop = POSTER_BASE_URL + series.backdropSeries

        Glide.with(containerView)
            .load(poster)
            .into(img_seriesFav)

        Glide.with(containerView)
            .load(backDrop)
            .into(img_backdrop_path_sFav)
    }

    override fun getLayout(): Int = R.layout.item_favourite_series
}