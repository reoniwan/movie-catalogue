package com.frozenproject.moviecatalogue.ui.catalogue.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.network.APICatalogueClient
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.data.repository.remote.MovieRemoteRepository
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var mContext: Context

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie, container, false)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(mContext))
            .get(MovieListViewModel::class.java)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movieCatalogueEntries = viewModel.movieEntries
        val networkStateView = viewModel.networkState
        val movieAdapter = MovieItemListAdapter(mContext)

        val gridLayoutManager = GridLayoutManager(mContext, 3)


        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.movieViewType) 1
                else 3
            }

        }

        rv_catalogue_movie.adapter = movieAdapter
        rv_catalogue_movie.layoutManager = gridLayoutManager
        rv_catalogue_movie.setHasFixedSize(true)

        movieCatalogueEntries.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        networkStateView.observe(this, Observer {
            progress_bar_movie.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_movie.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            //Settings Network
            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

    }

}
