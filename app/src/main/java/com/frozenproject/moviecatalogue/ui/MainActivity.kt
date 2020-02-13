package com.frozenproject.moviecatalogue.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.ui.adapter.SectionsPagerAdapter
import com.frozenproject.moviecatalogue.ui.catalogue.movie.MovieListFragment
import com.frozenproject.moviecatalogue.ui.catalogue.search.FindCatalogueActivity
import com.frozenproject.moviecatalogue.ui.catalogue.series.SeriesListFragment
import com.frozenproject.moviecatalogue.ui.catalogue.settings.SettingReminderActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view_pager
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.ic_video_library_white_24dp)

        supportActionBar?.elevation = 0f

        fab.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchMovie = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchMovie.getSearchableInfo(componentName))
        searchView.queryHint = "Type title of Movie or Tv Serial..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    val intent = Intent(this@MainActivity, FindCatalogueActivity::class.java)
                    intent.putExtra(FindCatalogueActivity.KEY_QUERY, query)
                    startActivity(intent)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(v: MenuItem): Boolean {
        if (v.itemId == R.id.action_change_settings) {
            val menuIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)

            startActivity(menuIntent)
        } else if(v.itemId == R.id.setting_reminder){
            val menuIntent = Intent(this, SettingReminderActivity::class.java)

            startActivity(menuIntent)
        }
        return super.onOptionsItemSelected(v)
    }


}
