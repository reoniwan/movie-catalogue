package com.frozenproject.moviecatalogue.ui.catalogue.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.UnitSpecificMovieCatalogueEntry
import com.frozenproject.moviecatalogue.data.network.APICatalogue
import com.frozenproject.moviecatalogue.data.network.CatalogueNetworkDataSourceImpl
import com.frozenproject.moviecatalogue.data.network.ConnectivityInterceptorImpl
import com.frozenproject.moviecatalogue.ui.base.ScopeFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.item_list_catalogue_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MovieListFragment : ScopeFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: MovieViewModelFactory by instance()

    private lateinit var viewModel: MovieListViewModel

    companion object{
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val movieCatalogueEntries = viewModel.movieEntries.await()

        movieCatalogueEntries.observe(this@MovieListFragment, Observer { movieEntries ->
            if (movieEntries == null) return@Observer

            initRecyclerView(movieEntries.toMovieCatalogueItems())
        })
    }

    private fun initRecyclerView(items: List<MovieItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        rv_catalogue_movie.apply {
            layoutManager = LinearLayoutManager(this@MovieListFragment.context, RecyclerView.VERTICAL, false)
            adapter = groupAdapter
        }

    }
}

    private fun List<UnitSpecificMovieCatalogueEntry>.toMovieCatalogueItems(): List<MovieItem> {
        return this.map {
            MovieItem(it)
        }
}
