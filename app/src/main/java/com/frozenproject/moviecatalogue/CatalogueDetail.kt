package com.frozenproject.moviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.model.CatalogueModel
import kotlinx.android.synthetic.main.layout_catalogue_detail.*

class CatalogueDetail : AppCompatActivity() {

//    companion object{
//        const val detailMovie = "DETAIL"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_catalogue_detail)

        val actionBar = supportActionBar
        actionBar?.title = ""

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val movieDetail: CatalogueModel? = intent.getParcelableExtra("DETAIL")

        txt_detail_name.text = movieDetail?.name

        txt_detail_genre.text = movieDetail?.genre

        txt_detail_rilis.text = movieDetail?.date

        txt_director_name.text = movieDetail?.director

        txt_rating_detail.text = movieDetail?.rating

        txt_detail.text = movieDetail?.desc

        Glide.with(this)
            .load(movieDetail?.image)
            .into(img_movie_detail)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
