package com.frozenproject.moviecatalogue.ui.catalogue.series


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.frozenproject.moviecatalogue.R
import kotlinx.android.synthetic.main.fragment_series.*

/**
 * A simple [Fragment] subclass.
 */
class SeriesListFragment : Fragment() {

    private lateinit var viewModel: SeriesListViewModel

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_series, container, false)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SeriesListViewModel::class.java)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val seriesCatalogueEntries = viewModel.getSeries()
        viewModel.setSeries(1)
        val seriesAdapter = SeriesItemListAdapter(mContext)
        seriesAdapter.notifyDataSetChanged()

        val gridLayoutManager = GridLayoutManager(mContext, 3)


        rv_catalogue_series.adapter = seriesAdapter
        rv_catalogue_series.layoutManager = gridLayoutManager
        rv_catalogue_series.setHasFixedSize(true)


        seriesCatalogueEntries.observe(this, Observer {
                seriesAdapter.setData(it)

        })

//        networkStateView.observe(this, Observer {
//            progress_bar_series.visibility =
//                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
//            txt_error_series.visibility =
//                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
//
//            //Settings Network
//            if (!viewModel.listIsEmpty()) {
//                seriesAdapter.setNetworkState(it)
//            }
//        })

    }


}
