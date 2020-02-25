package com.frozenproject.moviefavoriteclient.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviefavoriteclient.R
import com.frozenproject.moviefavoriteclient.model.MovieData
import com.frozenproject.moviefavoriteclient.model.constant.Constant
import kotlinx.android.synthetic.main.item_favourite_movie.view.*

class MovieListAdapter(
    private val context: Context?,
    private val listMovie:List<MovieData>
): RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_favourite_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movies: MovieData){
            itemView.title_favourite_movie.text = movies.title
            itemView.txt_release_favourite_movie.text = movies.releaseDate

            val posterMovie = Constant.POSTER_URL + movies.imageMovie
            Glide.with(itemView)
                .load(posterMovie)
                .into(itemView.img_movieFav)

            val backdropMovie = Constant.POSTER_URL + movies.backdrop
            Glide.with(itemView)
                .load(backdropMovie)
                .into(itemView.img_backdrop_path)

        }
    }
}