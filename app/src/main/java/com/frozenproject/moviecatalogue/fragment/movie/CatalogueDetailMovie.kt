package com.frozenproject.moviecatalogue.fragment.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.model.CatalogueModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*


class CatalogueDetailMovie : AppCompatActivity() {

    companion object {
        const val DETAIL = "DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail)

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""

        actionBar?.setDisplayHomeAsUpEnabled(true)

        //DETAIL MOVIE
        val movieDetail: CatalogueModel? = intent.getParcelableExtra(DETAIL)

        txt_title_movie_detail.text = movieDetail?.name
        txt_detail_genre.text = movieDetail?.genre
        txt_release_title.text = movieDetail?.date
        txt_director_name.text = movieDetail?.director
        txt_rating_detail.text = movieDetail?.rating
        txt_description.text = movieDetail?.desc

        Glide.with(this)
            .load(movieDetail?.image)
            .into(img_movie_detail)


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
