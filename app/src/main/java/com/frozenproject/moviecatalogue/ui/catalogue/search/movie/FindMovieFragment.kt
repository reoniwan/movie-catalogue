package com.frozenproject.moviecatalogue.ui.catalogue.search.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.ui.catalogue.home.movie.MovieItemListAdapter
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.fragment_find_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FindMovieFragment : Fragment() {

    private lateinit var viewModel: FindMovieViewModel

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_QUERY = "query"

        fun newInstance(query: String, index: Int): FindMovieFragment {
            val fragment =
                FindMovieFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            bundle.putString(ARG_QUERY, query)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var movieTitle = ""
        if (arguments != null) {
            movieTitle = arguments?.getString(ARG_QUERY) as String
        }


        viewModel = ViewModelProvider(
            this, Injection
                .provideViewModelFactorySearch(requireContext(), movieTitle)
        )
            .get(FindMovieViewModel::class.java)

        return inflater.inflate(R.layout.fragment_find_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val listMovieAdapter =
            MovieItemListAdapter(
                requireContext()
            )

        val gridLayoutManager = GridLayoutManager(context, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = listMovieAdapter.getItemViewType(position)
                return if (
                    viewType == listMovieAdapter.movieViewType) 1
                else 3
            }

        }
        rv_catalogue_search.layoutManager = gridLayoutManager
        rv_catalogue_search.setHasFixedSize(true)

        val movieCatalogueList = viewModel.searchMovieEntries
        val networkStateViewResult = viewModel.searchMovieNetworkState

        movieCatalogueList.observe(viewLifecycleOwner, Observer {
            listMovieAdapter.submitList(it)
        })

        networkStateViewResult.observe(viewLifecycleOwner, Observer {
            pb_catalogue_search.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_catalogue_search_error.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            //Settings Network
            if (!viewModel.listIsEmpty()) {
                listMovieAdapter.setNetworkState(it)
            }
        })

        rv_catalogue_search.adapter = listMovieAdapter
    }


}
