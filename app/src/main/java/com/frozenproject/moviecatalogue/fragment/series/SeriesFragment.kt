package com.frozenproject.moviecatalogue.fragment.series


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frozenproject.moviecatalogue.fragment.movie.CatalogueDetailMovie

import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.adapter.CatalogueSeriesAdapter
import com.frozenproject.moviecatalogue.callback.ISeriesClickListener
import com.frozenproject.moviecatalogue.model.SeriesCatalogueModel

/**
 * A simple [Fragment] subclass.
 */
class SeriesFragment : Fragment() {

    private lateinit var recycleSeries: RecyclerView
    private var listCatalogueSeries = ArrayList<SeriesCatalogueModel>()

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): SeriesFragment {
            val fragment =
                SeriesFragment()
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
        val root = inflater.inflate(R.layout.fragment_series, container, false)

        recycleSeries = root.findViewById(R.id.rv_catalogue_series)
        recycleSeries.layoutManager = LinearLayoutManager(context)

        val adapter = CatalogueSeriesAdapter(listCatalogueSeries)
        recycleSeries.adapter = adapter

        adapter.setOnClickCallback(object : ISeriesClickListener {
            override fun onItemClicked(data: SeriesCatalogueModel) {

                val intent = Intent(this@SeriesFragment.context, CatalogueDetailSeries::class.java)
                intent.putExtra(CatalogueDetailSeries.DETAIL, data)
                startActivity(intent)
            }
        })

        initViews()

        return root
    }

    private fun initViews(): ArrayList<SeriesCatalogueModel> {
        val imgCatalogue = resources.obtainTypedArray(R.array.image_series_film)
        val nameFilm = resources.getStringArray(R.array.series_name)
        val ratingFilm = resources.getStringArray(R.array.rating_series)
        val studioFilm = resources.getStringArray(R.array.studio)
        val genreFilm = resources.getStringArray(R.array.genre_series)
        val dateFilm = resources.getStringArray(R.array.release_series)
        val descFilm = resources.getStringArray(R.array.film_series_desc)
        listCatalogueSeries.clear()

        for (i in nameFilm.indices) {
            listCatalogueSeries.add(
                SeriesCatalogueModel(
                    nameFilm[i],
                    ratingFilm[i],
                    studioFilm[i],
                    dateFilm[i],
                    imgCatalogue.getResourceId(i, 0),
                    genreFilm[i],
                    descFilm[i]
                )
            )
        }
        imgCatalogue.recycle()
        return listCatalogueSeries
    }


}
