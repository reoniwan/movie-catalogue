package com.frozenproject.moviecatalogue.ui.catalogue.series

import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.callback.SeriesCallback
import com.frozenproject.moviecatalogue.data.db.ResultSeries
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import kotlinx.android.synthetic.main.item_list_catalogue_series.view.*
import kotlinx.android.synthetic.main.network_state_movie.view.*

class SeriesItemListAdapter(private var context: Context) :
    RecyclerView.Adapter<SeriesItemListAdapter.SeriesItemViewHolder>() {

    private val mData = ArrayList<ResultSeries>()

    private lateinit var seriesListViewModel : SeriesListViewModel

    fun setData(items: ArrayList<ResultSeries>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesItemViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View

        view = layoutInflater.inflate(R.layout.item_list_catalogue_series, parent, false)
        return SeriesItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: SeriesItemViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size


    inner class SeriesItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        fun bind(series: ResultSeries) {
            itemView.txt_title_series.text = series.title
            itemView.txt_release_series.text = series.dateSeries
            itemView.txt_rating_series.text = series.ratingSeries.toString()

            val seriesImagePoster = POSTER_BASE_URL + series.imageSeries

            Glide.with(itemView.context)
                .load(seriesImagePoster)
                .into(itemView.img_series)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

            val position = adapterPosition
            val data = mData[position]


            val intent = Intent(itemView.context, DetailSeriesActivity::class.java)
            intent.putExtra(DetailSeriesActivity.DETAIL_SERIES, mData)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            itemView.context.startActivity(intent)
        }
    }

}