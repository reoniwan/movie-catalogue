package com.frozenproject.moviecatalogue.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.ui.adapter.SectionsPagerAdapter
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(v: MenuItem): Boolean {
        if (v.itemId == R.id.action_change_settings) {
            val menuIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)

            startActivity(menuIntent)
        }
        return super.onOptionsItemSelected(v)
    }


}
