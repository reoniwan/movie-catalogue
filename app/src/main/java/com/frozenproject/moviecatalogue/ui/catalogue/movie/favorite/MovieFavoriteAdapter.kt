package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

import android.app.Activity
import android.content.Context
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
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.CATALOGUE_ID
import com.frozenproject.moviecatalogue.data.network.ID
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.utils.CustomOnItemClickListener
import kotlinx.android.synthetic.main.item_favourite_movie.view.*

class MovieFavoriteAdapter(
    private val activity: Activity
): RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavViewModel>() {

    private var listMovies = ArrayList<MovieDetail>()
            set(listMovies) {
                if (listMovies.size > 0){
                    this.listMovies.clear()
                }

                this.listMovies.addAll(listMovies)

                notifyDataSetChanged()
            }

    fun addItem(movie: MovieDetail){
        this.listMovies.add(movie)
        notifyItemInserted(this.listMovies.size - 1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavViewModel {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favourite_movie, parent, false)

        return MovieFavViewModel(view)
    }

    override fun onBindViewHolder(holder: MovieFavViewModel, position: Int) {
        holder.bind(listMovies[position])
    }

    inner class MovieFavViewModel(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieDetail?){
            with(itemView){
                title_favourite_movie.text = movie?.title
                txt_release_favourite_movie.text = movie?.releaseDate

                val movieImagePoster = POSTER_BASE_URL + movie?.posterPath
                Glide.with(itemView)
                    .load(movieImagePoster)
                    .into(img_movieFav)

                cv_favorite_movie.setOnClickListener(
                    CustomOnItemClickListener(adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallBack{
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, MovieDetailActivity::class.java)
                                intent.putExtra(ID, CATALOGUE_ID)
                                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
                                intent.putExtra(MovieDetailActivity.IS_FAVORITE, true)
                            }

                        }))
            }
        }
    }

    override fun getItemCount(): Int = listMovies.size
}