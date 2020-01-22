package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

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
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity

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

    private val adapterList: MovieFavoriteAdapter by lazy {
        MovieFavoriteAdapter{ goToDetailMovies(it) }
    }

    private lateinit var viewModel: MovieFavoriteViewModel
    lateinit var favoriteRepository: MovieCatalogueRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favourite_movie, container, false)


        viewModel = getViewModel()


        viewModel.favMovies.observe(this@MovieFavoriteFragment, Observer<PagedList<ResultMovie>> {
            adapterList.submitList(it)
        })

        return root
    }

    private fun getViewModel(): MovieFavoriteViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieFavoriteViewModel(favoriteRepository) as T
            }
        })[MovieFavoriteViewModel::class.java]
    }

    private fun goToDetailMovies(movies: ResultMovie){
        val i = Intent(activity, MovieDetailActivity::class.java)
        i.putExtra("liveDataMovies", movies)
        i.putExtra("isFromFavorite", true)
        startActivity(i)
    }
}