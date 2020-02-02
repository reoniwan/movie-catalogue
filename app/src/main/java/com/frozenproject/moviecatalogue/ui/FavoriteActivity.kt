package com.frozenproject.moviecatalogue.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.ui.adapter.SectionPagerFavorite
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val sectionsPagerAdapter = SectionPagerFavorite(this, supportFragmentManager)
        view_pager_favorite.adapter = sectionsPagerAdapter
        tabs_favorite.setupWithViewPager(view_pager_favorite)

        setSupportActionBar(toolbar_favorite)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.favorite)

        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}