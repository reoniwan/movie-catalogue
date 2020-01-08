package com.frozenproject.moviecatalogue.ui.catalogue.series


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.network.APICatalogueClient
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.SeriesCatalogueRepository
import kotlinx.android.synthetic.main.fragment_series.*

/**
 * A simple [Fragment] subclass.
 */
class SeriesListFragment : Fragment() {

    private lateinit var viewModel: SeriesListViewModel
    lateinit var seriesRepository: SeriesCatalogueRepository
    private lateinit var mContext: Context

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): SeriesListFragment {
            val fragment =
                SeriesListFragment()
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
        val root = inflater.inflate(R.layout.fragment_series, container, false)

        val apiService: APICatalogueInterface = APICatalogueClient.getClient()
        seriesRepository = SeriesCatalogueRepository(apiService)
        viewModel = getViewModel()


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val seriesCatalogueEntries = viewModel.seriesEntries
        val networkStateView = viewModel.networkState
        val seriesAdapter = SeriesItemListAdapter(mContext)

        val gridLayoutManager = GridLayoutManager(mContext, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = seriesAdapter.getItemViewType(position)
                return if (viewType == seriesAdapter.seriesViewType) 1
                else 3
            }

        }

        rv_catalogue_series.adapter = seriesAdapter
        rv_catalogue_series.layoutManager = gridLayoutManager
        rv_catalogue_series.setHasFixedSize(true)

        seriesCatalogueEntries.observe(this, Observer {
            seriesAdapter.submitList(it)
        })

        networkStateView.observe(this, Observer {
            progress_bar_series.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_series.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            //Settings Network
            if (!viewModel.listIsEmpty()) {
                seriesAdapter.setNetworkState(it)
            }
        })

    }


    private fun getViewModel(): SeriesListViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SeriesListViewModel(seriesRepository) as T
            }
        })[SeriesListViewModel::class.java]
    }

}
