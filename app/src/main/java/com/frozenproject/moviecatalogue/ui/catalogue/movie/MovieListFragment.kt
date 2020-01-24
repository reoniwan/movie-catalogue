package com.frozenproject.moviecatalogue.ui.catalogue.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueClient
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.data.repository.remote.MovieRemoteRepository
import com.frozenproject.moviecatalogue.databinding.FragmentMovieBinding
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite.MovieFavoriteAdapter
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var mContext: Context
    private lateinit var binding: FragmentMovieBinding

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): MovieListFragment {
            val fragment =
                MovieListFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val adapterMovie: MovieFavoriteAdapter by lazy {
        MovieFavoriteAdapter{ goToDetailsMovies(it) }
    }

    private fun goToDetailsMovies(movies: ResultMovie) {
        val i = Intent(mContext, MovieFavoriteAdapter::class.java)
        i.putExtra(MovieDetailActivity.EXTRA_MOVIE, movies)
        i.putExtra(MovieDetailActivity.IS_FAVORITE, true)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater,R.layout.fragment_movie, container, false)
        binding.lifecycleOwner = this@MovieListFragment

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(mContext))
            .get(MovieListViewModel::class.java)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.rvCatalogueMovie.apply {
            adapter = adapterMovie
            layoutManager = GridLayoutManager(mContext, 3)
            setHasFixedSize(true)
        }

        viewModel.movieList.observe(this, Observer<PagedList<ResultMovie>>{
            adapterMovie.submitList(it)
        })

    }

}
