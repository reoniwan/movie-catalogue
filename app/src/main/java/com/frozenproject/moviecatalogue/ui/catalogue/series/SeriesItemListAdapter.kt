package com.frozenproject.moviecatalogue.ui.catalogue.series

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.callback.SeriesCallback
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.ID
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.frozenproject.moviecatalogue.ui.catalogue.series.detail.DetailSeriesActivity
import kotlinx.android.synthetic.main.item_list_catalogue_series.view.*
import kotlinx.android.synthetic.main.network_state.view.*

class SeriesItemListAdapter(private var context: Context) :
    PagedListAdapter<ResultSeries, RecyclerView.ViewHolder>(SeriesCallback()) {

    val seriesViewType = 1
    private val networkViewType = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View

        return if (viewType == seriesViewType) {
            view = layoutInflater.inflate(R.layout.item_list_catalogue_series, parent, false)
            SeriesItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state, parent, false)
            NetworkStateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == seriesViewType) {
            (holder as SeriesItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateViewHolder).bind(networkState)
        }
    }

    class SeriesItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(series: ResultSeries?, context: Context) {
            itemView.txt_title_series.text = series?.titleSeries
            itemView.txt_release_series.text = series?.releaseDate
            itemView.txt_rating_series.text = series?.ratingSeries.toString()

            val seriesImagePoster = POSTER_BASE_URL + series?.imageSeries
            Glide.with(itemView.context)
                .load(seriesImagePoster)
                .into(itemView.img_series)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailSeriesActivity::class.java)
                intent.putExtra(ID, series?.idSeries)
                intent.putExtra(DetailSeriesActivity.IS_FAVORITE_SERIES, false)
                intent.putExtra(DetailSeriesActivity.EXTRA_SERIES, series)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }


    class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: NetworkState?) {

            //Progress Bar Running when loaded data
            if (networkState != null) {
                itemView.progress_bar_item_movie.visibility = View.VISIBLE
            } else {
                itemView.progress_bar_item_movie.visibility = View.GONE
            }

            //No Connection or End of list
            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item_movie.visibility = View.VISIBLE
                itemView.error_msg_item_movie.text = networkState.msg
            } else {
                itemView.error_msg_item_movie.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.END_LIST) {
                itemView.error_msg_item_movie.visibility = View.VISIBLE
                itemView.error_msg_item_movie.text = networkState.msg
            } else {
                itemView.error_msg_item_movie.visibility = View.GONE
            }

        }

    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            networkViewType
        } else {
            seriesViewType
        }

    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()

        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) { //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount()) //remove the progressbar at the end
            } else { //hadExtraRow is false and hasExtraRow true
                notifyItemInserted(super.getItemCount())//add the progressbar at the end
            }
        } else if (hasExtraRow() && previousState != newNetworkState) { //hasExtraRow and hadExtraRow is true
            notifyItemChanged(itemCount - 1)
        }

    }

}