package com.frozenproject.moviecatalogue.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.internal.ISeriesClickListener
import com.frozenproject.moviecatalogue.ui.model.SeriesCatalogueModel
import kotlinx.android.synthetic.main.item_list_catalogue_series.view.*

class CatalogueSeriesAdapter(
    private val listCatalogueSeries: ArrayList<SeriesCatalogueModel>
) : RecyclerView.Adapter<CatalogueSeriesAdapter.ViewHolder>() {

    private var onClickCallback: ISeriesClickListener? = null

    fun setOnClickCallback(onClickCallback: ISeriesClickListener) {
        this.onClickCallback = onClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_catalogue_series, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listCatalogueSeries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listCatalogueSeries[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(listCatalogueSeries: SeriesCatalogueModel) {
            with(itemView) {

                txt_title_series.text = listCatalogueSeries.name
                txt_release_series.text = listCatalogueSeries.date
                txt_rating_series.text = listCatalogueSeries.rating

                Glide.with(itemView)
                    .load(listCatalogueSeries.image)
                    .into(img_series)

                itemView.setOnClickListener { onClickCallback?.onItemClicked(listCatalogueSeries) }
            }
        }
    }
}