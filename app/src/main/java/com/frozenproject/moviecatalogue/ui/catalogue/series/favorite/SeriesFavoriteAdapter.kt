package com.frozenproject.moviecatalogue.ui.catalogue.series.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.callback.SeriesCallback
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import kotlinx.android.synthetic.main.item_favourite_series.view.*

class SeriesFavoriteAdapter(
    private val onclick: (ResultSeries) -> Unit
) : PagedListAdapter<ResultSeries, SeriesFavoriteAdapter.SeriesFavViewModel>(SeriesCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesFavViewModel {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favourite_series, parent, false)

        return SeriesFavViewModel(view)
    }

    override fun onBindViewHolder(holder: SeriesFavViewModel, position: Int) {
        getItem(position)?.let { data ->
            holder.bind(data, onclick)
        }
    }

    inner class SeriesFavViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(series: ResultSeries, onclick: (ResultSeries) -> Unit) {
            with(itemView) {
                title_favourite_series.text = series.titleSeries
                txt_release_favourite_series.text = series.releaseDate

                val seriesImagePoster = POSTER_BASE_URL + series.imageSeries
                Glide.with(itemView)
                    .load(seriesImagePoster)
                    .into(img_seriesFav)

                val backDropImage = POSTER_BASE_URL + series.backdropSeries
                Glide.with(itemView)
                    .load(backDropImage)
                    .into(img_backdrop_path_sFav)

                itemView.setOnClickListener { onclick(series) }
            }
        }
    }
}