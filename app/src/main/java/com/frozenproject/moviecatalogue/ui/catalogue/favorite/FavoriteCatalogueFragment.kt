package com.frozenproject.moviecatalogue.ui.catalogue.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.ID
import com.frozenproject.moviecatalogue.provider.CatalogueProvider
import com.frozenproject.moviecatalogue.ui.catalogue.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.utils.CoroutineScopedFragment
import com.frozenproject.moviecatalogue.utils.MappingHelper
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_favourite_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteCatalogueFragment : CoroutineScopedFragment() {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): FavoriteCatalogueFragment {
            val fragment =
                FavoriteCatalogueFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_favourite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            when (arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int) {
                1 -> loadMoviesAsync()
                2 -> loadSeriesAsync()
            }
        }
    }

    @SuppressLint("Recycle")
    private fun loadSeriesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val uriFavoriteSeries = Uri.parse("${CatalogueProvider.URI_SERIES}/1")
            val deferred = async(Dispatchers.IO) {
                val cursor =
                    activity?.contentResolver?.query(
                        uriFavoriteSeries,
                        null,
                        null,
                        null,
                        null,
                        null
                    ) as Cursor
                MappingHelper.mapCursorToListSeries(cursor)
            }
            val series = deferred.await()
            if (series.isNotEmpty()) {
                initSeriesRecycle(series.toSeriesItems())
                txt_empty_cart.visibility = View.GONE
            }
        }
    }

    private fun List<ResultSeries>.toSeriesItems(): List<SeriesItemFav> {
        return this.map {
            SeriesItemFav(
                it
            )
        }
    }

    private fun initSeriesRecycle(items: List<SeriesItemFav>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recycler_favourite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteCatalogueFragment.mContext)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            showDetails((item as SeriesItemFav).series.idSeries, view, false)
        }
    }

    @SuppressLint("Recycle")
    private fun loadMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val uriFavoriteMovie = Uri.parse("${CatalogueProvider.URI_MOVIE}/1")
            val deferred = async(Dispatchers.IO) {
                val cursor =
                    activity?.contentResolver?.query(
                        uriFavoriteMovie,
                        null,
                        null,
                        null,
                        null,
                        null
                    ) as Cursor
                MappingHelper.mapCursorToListMovie(cursor)
            }
            val movies = deferred.await()
            if (movies.isNotEmpty()) {
                initMovieRecycle(movies.toMovieItems())
                txt_empty_cart.visibility = View.GONE
            }
        }
    }

    private fun initMovieRecycle(items: List<MovieItemFav>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recycler_favourite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteCatalogueFragment.mContext)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            showDetails((item as MovieItemFav).movie.idMovie, view, true)
        }
    }

    private fun List<ResultMovie>.toMovieItems(): List<MovieItemFav> {
        return this.map {
            MovieItemFav(
                it
            )
        }
    }

    private fun showDetails(idMovie: Int, view: View, isFav: Boolean) {
        val intent = Intent(view.context, MovieDetailActivity::class.java)
        intent.putExtra(ID, idMovie)
        intent.putExtra(MovieDetailActivity.IS_FAVORITE, isFav)
        startActivity(intent)
    }

}