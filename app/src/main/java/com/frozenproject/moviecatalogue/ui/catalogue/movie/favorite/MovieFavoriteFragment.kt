package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.CATALOGUE_ID
import com.frozenproject.moviecatalogue.data.network.ID
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.movie.MovieItemListAdapter
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.fragment_favourite_movie.*

class MovieFavoriteFragment : Fragment() {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): MovieFavoriteFragment {
            val fragment =
                MovieFavoriteFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }


    private fun goToDetailMovie(movie: ResultMovie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
        intent.putExtra(ID, CATALOGUE_ID)
        intent.putExtra(MovieDetailActivity.IS_FAVORITE, true)
        startActivity(intent)
    }

    private lateinit var viewModel: MovieFavoriteViewModel
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favourite_movie, container, false)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(mContext))
            .get(MovieFavoriteViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterList = MovieItemListAdapter(mContext)

        recycler_favourite.apply {
            adapter = adapterList
            layoutManager = GridLayoutManager(mContext, 3)
            setHasFixedSize(true)
        }

        viewModel.favMovies.observe(this@MovieFavoriteFragment, Observer<PagedList<ResultMovie>> {
            adapterList.submitList(it)
        })
    }

}