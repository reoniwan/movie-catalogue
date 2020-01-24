package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.databinding.FragmentFavouriteMovieBinding
import com.frozenproject.moviecatalogue.ui.catalogue.movie.MovieAdapter
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.utils.Injection

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

    private val adapterMovie: MovieAdapter by lazy {
        MovieAdapter { goToDetailsMovies(it)
        }
    }

    private fun goToDetailsMovies(it: ResultMovie) {
        val i = Intent(activity, MovieDetailActivity::class.java)
        i.putExtra(MovieDetailActivity.EXTRA_MOVIE, it)
        i.putExtra(MovieDetailActivity.IS_FAVORITE, true)
    }

    private lateinit var viewModel: MovieFavoriteViewModel
    private lateinit var mContext: Context
    private lateinit var binding : FragmentFavouriteMovieBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favourite_movie, container, false)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(requireContext()))
            .get(MovieFavoriteViewModel::class.java)

        binding.recyclerFavourite.apply {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        viewModel.favMovies.observe(this@MovieFavoriteFragment, Observer<PagedList<ResultMovie>> {
            adapterMovie.submitList(it)
        })

        return binding.root
    }

}