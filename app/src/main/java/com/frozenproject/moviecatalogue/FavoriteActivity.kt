package com.frozenproject.moviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frozenproject.moviecatalogue.ui.adapter.SectionPagerFavorite
import com.frozenproject.moviecatalogue.ui.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class FavoriteActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val sectionsPagerAdapter = SectionPagerFavorite(this, supportFragmentManager)
        view_pager_favorite.adapter = sectionsPagerAdapter
        tabs_favorite.setupWithViewPager(view_pager_favorite)

        setSupportActionBar(toolbar)

        supportActionBar?.elevation = 0f
    }
}