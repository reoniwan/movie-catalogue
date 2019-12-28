package com.frozenproject.moviecatalogue.ui.catalogue.movie

import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.database.entity.ResultMovie
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.MovieSpesificCatalogueEntry
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.UnitSpecificMovieCatalogueEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_list_catalogue_movie.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*

class MovieItem(
    private val movieEntry: MovieSpesificCatalogueEntry
): Item() {

    override fun bind(viewHolder: ViewHolder, position: Int){
        viewHolder.apply {
            title.text = movieEntry.titleMovie
            txt_release.text = movieEntry.release
            txt_rating_movie.text = movieEntry.rating.toString()

            Glide.with(this.containerView)
                .load("https://image.tmdb.org/t/p/w185" + movieEntry.imageUrl)
                .into(img_movie)
        }
    }

    override fun getLayout() = R.layout.item_list_catalogue_movie

}