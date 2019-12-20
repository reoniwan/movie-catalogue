package com.frozenproject.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.callback.IMovieClickListener
import com.frozenproject.moviecatalogue.model.CatalogueModel
import kotlinx.android.synthetic.main.item_list_catalogue_movie.view.*

class CatalogueMovieAdapter(
    private val listCatalogue: ArrayList<CatalogueModel>
) : RecyclerView.Adapter<CatalogueMovieAdapter.ViewHolder>() {

    private var onClickCallback: IMovieClickListener? = null

    fun setOnClickCallback(onClickCallback: IMovieClickListener) {
        this.onClickCallback = onClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_list_catalogue_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listCatalogue.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listCatalogue[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(listMovie: CatalogueModel) {
            with(itemView) {
                txt_title_movie.text = listMovie.name
                txt_release_movie.text = listMovie.date
                txt_rating_movie.text = listMovie.rating

                Glide.with(itemView)
                    .load(listMovie.image)
                    .into(img_movie)

                itemView.setOnClickListener { onClickCallback?.onItemClicked(listMovie) }
            }
        }
    }

}