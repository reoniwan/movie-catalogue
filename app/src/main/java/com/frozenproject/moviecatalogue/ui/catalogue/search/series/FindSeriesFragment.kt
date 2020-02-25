package com.frozenproject.moviecatalogue.ui.catalogue.search.series


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
import com.frozenproject.moviecatalogue.ui.catalogue.home.series.SeriesItemListAdapter
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.fragment_find_series.*

/**
 * A simple [Fragment] subclass.
 */
class FindSeriesFragment : Fragment() {

    private lateinit var viewModel: FindSeriesViewModel

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_QUERY_SERIES = "query_series"

        fun newInstance(query: String, index: Int): FindSeriesFragment {
            val fragment = FindSeriesFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            bundle.putString(ARG_QUERY_SERIES, query)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var seriesTitle = ""
        if (arguments != null) {
            seriesTitle = arguments?.getString(ARG_QUERY_SERIES) as String
        }

        viewModel = ViewModelProvider(
            this, Injection
                .provideViewModelFactorySearch(requireContext(), seriesTitle)
        )
            .get(FindSeriesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_find_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listSeriesAdapter =
            SeriesItemListAdapter(
                requireContext()
            )

        val gridLayoutManager = GridLayoutManager(context, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = listSeriesAdapter.getItemViewType(position)
                return if (
                    viewType == listSeriesAdapter.seriesViewType) 1
                else 3
            }

        }
        rv_series_search.layoutManager = gridLayoutManager
        rv_series_search.setHasFixedSize(true)
        rv_series_search.adapter = listSeriesAdapter

        val seriesCatalogueList =
            viewModel.searchSeriesEntries
        val networkStateViewResultSeries =
            viewModel.searchSeriesNetworkState

        seriesCatalogueList.observe(viewLifecycleOwner, Observer {
            listSeriesAdapter.submitList(it)
        })

        networkStateViewResultSeries.observe(viewLifecycleOwner, Observer {
            pb_series_search.visibility =
                if (viewModel.listSeriesIsEmpty() && it == NetworkState.LOADING)
                    View.VISIBLE else View.GONE
            txt_series_search_error.visibility =
                if (viewModel.listSeriesIsEmpty() && it == NetworkState.ERROR)
                    View.VISIBLE else View.GONE

            //Settings Network
            if (!viewModel.listSeriesIsEmpty()) {
                listSeriesAdapter.setNetworkState(it)
            }
        })
    }


}
