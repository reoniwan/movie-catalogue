package com.frozenproject.moviecatalogue.ui.catalogue.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.ui.adapter.SearchPagerAdapter
import com.frozenproject.moviecatalogue.ui.adapter.SectionPagerFavorite
import com.frozenproject.moviecatalogue.ui.catalogue.movie.MovieItemListAdapter
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_find_catalogue.*
import kotlinx.android.synthetic.main.fragment_movie.*
import java.lang.Exception

class FindCatalogueActivity : AppCompatActivity() {

    companion object{
        const val KEY_QUERY = "key_query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_catalogue)

        val query = intent.getStringExtra(KEY_QUERY)

        val sectionsPagerAdapter = SearchPagerAdapter(this, supportFragmentManager, query)
        view_pager_search.adapter = sectionsPagerAdapter
        tabs_search.setupWithViewPager(view_pager_search)

        setSupportActionBar(toolbar_search)

    }

}
