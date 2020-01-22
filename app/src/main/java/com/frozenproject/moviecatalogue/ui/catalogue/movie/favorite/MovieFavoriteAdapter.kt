package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.callback.MovieCallback
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.ID
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.item_favourite_movie.view.*

class MovieFavoriteAdapter(
    private val onClick: (ResultMovie) -> Unit
): PagedListAdapter<ResultMovie, MovieFavoriteAdapter.MovieFavViewModel>(MovieCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavViewModel {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favourite_movie, parent, false)

        return MovieFavViewModel(view)
    }

    override fun onBindViewHolder(holder: MovieFavViewModel, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class MovieFavViewModel(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movie: ResultMovie?, onClick: (ResultMovie) -> Unit){
            with(itemView){
                title_favourite_movie.text = movie?.title
                txt_release_favourite_movie.text = movie?.releaseDate

                val movieImagePoster = POSTER_BASE_URL + movie?.imageMovie
                Glide.with(itemView)
                    .load(movieImagePoster)
                    .into(img_movieFav)

                itemView.setOnClickListener{ movie?.let { data -> onClick(data) } }
            }
        }
    }
}