package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.callback.MovieCallback
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.CATALOGUE_ID
import com.frozenproject.moviecatalogue.data.network.ID
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.frozenproject.moviecatalogue.databinding.ItemListCatalogueMovieBinding
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.utils.CustomOnItemClickListener
import kotlinx.android.synthetic.main.item_favourite_movie.view.*

class MovieFavoriteAdapter(
    private val onClick: (ResultMovie) -> Unit
): PagedListAdapter<ResultMovie, MovieFavoriteAdapter.MovieFavViewModel>(MovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListCatalogueMovieBinding.inflate(inflater, parent, false)

        return MovieFavViewModel(binding)
    }

    override fun onBindViewHolder(holder: MovieFavViewModel, position: Int) {
        val movie = getItem(position)
        holder.apply {
            movie?.let { data->
                bind(data)
                itemView.setOnClickListener { onClick(data) }
            }
        }
    }

    inner class MovieFavViewModel(
        private val binding: ItemListCatalogueMovieBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: ResultMovie){
            binding.apply {
                model = movie
                executePendingBindings()
            }
        }
    }

}