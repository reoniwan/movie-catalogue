package com.frozenproject.moviecatalogue.ui.catalogue.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.ui.adapter.SearchPagerAdapter
import kotlinx.android.synthetic.main.activity_find_catalogue.*
import java.lang.Exception

class FindCatalogueActivity : AppCompatActivity() {

    companion object {
        const val KEY_QUERY = "key_query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_catalogue)

        val query = intent.getStringExtra(KEY_QUERY) ?: throw  Exception()

        val sectionsPagerAdapter = SearchPagerAdapter(this, supportFragmentManager, query)
        view_pager_search.adapter = sectionsPagerAdapter
        tabs_search.setupWithViewPager(view_pager_search)

        setSupportActionBar(toolbar_search)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.search_result)
        actionBar?.subtitle = "\"$query\""

        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
