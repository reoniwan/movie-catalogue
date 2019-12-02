package com.frozenproject.moviecatalogue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.frozenproject.moviecatalogue.adapter.CatalogueAdapter
import com.frozenproject.moviecatalogue.model.CatalogueModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listCatalogue: MutableList<CatalogueModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.title = "TOP FILM"

        initData()

//        rv_catalogue.setHasFixedSize(true)
        rv_catalogue.layoutManager = LinearLayoutManager(this)

        rv_catalogue.adapter = CatalogueAdapter(listCatalogue, this) {
            val movieDetail = Intent(this, CatalogueDetail::class.java)
            movieDetail.putExtra("DETAIL", it)
            startActivity(movieDetail)
        }
    }

    private fun initData() {
        val imgCatalogue = resources.obtainTypedArray(R.array.image_film)
        val nameFilm = resources.getStringArray(R.array.film_name)
        val ratingFilm = resources.getStringArray(R.array.Rating)
        val directorFilm = resources.getStringArray(R.array.directors)
        val genreFilm = resources.getStringArray(R.array.Genre)
        val dateFilm = resources.getStringArray(R.array.release)
        val descFilm = resources.getStringArray(R.array.film_desc)
        listCatalogue.clear()

        for (i in nameFilm.indices) {
            listCatalogue.add(
                CatalogueModel(
                    nameFilm[i],
                    ratingFilm[i],
                    directorFilm[i],
                    dateFilm[i],
                    imgCatalogue.getResourceId(i, 0),
                    genreFilm[i],
                    descFilm[i]
                )
            )
        }
        imgCatalogue.recycle()
    }
}
