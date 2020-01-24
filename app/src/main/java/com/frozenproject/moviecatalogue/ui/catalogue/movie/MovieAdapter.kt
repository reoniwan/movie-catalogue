package com.frozenproject.moviecatalogue.ui.catalogue.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frozenproject.moviecatalogue.callback.MovieCallback
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.databinding.ItemListCatalogueMovieBinding

class MovieAdapter(
    private val onClick: (ResultMovie) -> Unit
): PagedListAdapter<ResultMovie, MovieAdapter.MovieFavViewModel>(MovieCallback()) {

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