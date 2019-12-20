package com.frozenproject.moviecatalogue.fragment.movie


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.adapter.CatalogueMovieAdapter
import com.frozenproject.moviecatalogue.callback.IMovieClickListener
import com.frozenproject.moviecatalogue.model.CatalogueModel

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var recycleMovie: RecyclerView
    private val listCatalogue = ArrayList<CatalogueModel>()

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): MovieFragment {
            val fragment =
                MovieFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie, container, false)

        recycleMovie = root.findViewById(R.id.rv_catalogue_movie)
        recycleMovie.layoutManager = LinearLayoutManager(context)

        val adapter = CatalogueMovieAdapter(listCatalogue)
        recycleMovie.adapter = adapter

        adapter.setOnClickCallback(object : IMovieClickListener {
            override fun onItemClicked(data: CatalogueModel) {

                val intent = Intent(this@MovieFragment.context, CatalogueDetailMovie::class.java)
                intent.putExtra(CatalogueDetailMovie.DETAIL, data)
                startActivity(intent)
            }
        })

        initViews()

        return root
    }

    private fun initViews(): ArrayList<CatalogueModel> {
        val imgCatalogue = resources.obtainTypedArray(R.array.image_film)
        val nameFilm = resources.getStringArray(R.array.film_name)
        val ratingFilm = resources.getStringArray(R.array.Rating)
        val directorFilm = resources.getStringArray(R.array.directors)
        val genreFilm = resources.getStringArray(R.array.Genre)
        val dateFilm = resources.getStringArray(R.array.release)
        val descFilm = resources.getStringArray(R.array.film_desc)
        listCatalogue.clear()

        for (i in nameFilm.indices) {
            val detail = CatalogueModel(
                nameFilm[i],
                ratingFilm[i],
                directorFilm[i],
                dateFilm[i],
                imgCatalogue.getResourceId(i, 0),
                genreFilm[i],
                descFilm[i]
            )
            listCatalogue.add(detail)
        }
        imgCatalogue.recycle()
        return listCatalogue

    }


}
