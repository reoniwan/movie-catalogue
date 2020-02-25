package com.frozenproject.moviecatalogue.ui.catalogue.favorite

import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_favourite_movie.*

class MovieItemFav(
    val movie: ResultMovie
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            bindUI()
        }
    }

    private fun ViewHolder.bindUI() {
        title_favourite_movie.text = movie.title
        txt_release_favourite_movie.text = movie.releaseDate

        val poster = POSTER_BASE_URL + movie.imageMovie
        val backDrop = POSTER_BASE_URL + movie.backdrop

        Glide.with(containerView)
            .load(poster)
            .into(img_movieFav)

        Glide.with(containerView)
            .load(backDrop)
            .into(img_backdrop_path)
    }

    override fun getLayout(): Int = R.layout.item_favourite_movie


}