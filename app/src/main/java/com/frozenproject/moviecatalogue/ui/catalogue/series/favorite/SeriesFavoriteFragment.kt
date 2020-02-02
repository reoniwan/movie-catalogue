package com.frozenproject.moviecatalogue.ui.catalogue.series.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.ID
import com.frozenproject.moviecatalogue.ui.catalogue.series.detail.DetailSeriesActivity
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.fragment_favourite_series.*

class SeriesFavoriteFragment : Fragment() {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): SeriesFavoriteFragment {
            val fragment =
                SeriesFavoriteFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val adapterList: SeriesFavoriteAdapter by lazy {
        SeriesFavoriteAdapter { goToDetailSeries(it) }
    }
    private lateinit var viewModel: SeriesFavoriteViewModel
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favourite_series, container, false)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(mContext))
            .get(SeriesFavoriteViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_favourite_series.apply {
            adapter = adapterList
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

        }

        viewModel.favSeries.observe(viewLifecycleOwner, Observer<PagedList<ResultSeries>> {
            adapterList.submitList(it)
        })
    }

    private fun goToDetailSeries(series: ResultSeries) {
        val intent = Intent(activity, DetailSeriesActivity::class.java)
        intent.putExtra(DetailSeriesActivity.EXTRA_SERIES, series)
        intent.putExtra(ID, series.idSeries)
        intent.putExtra(DetailSeriesActivity.IS_FAVORITE_SERIES, true)
        startActivity(intent)
    }
}