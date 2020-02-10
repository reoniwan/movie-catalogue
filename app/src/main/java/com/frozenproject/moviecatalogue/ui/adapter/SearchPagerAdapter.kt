package com.frozenproject.moviecatalogue.ui.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite.MovieFavoriteFragment
import com.frozenproject.moviecatalogue.ui.catalogue.search.FindCatalogueFragment
import com.frozenproject.moviecatalogue.ui.catalogue.search.series.FindSeriesFragment
import com.frozenproject.moviecatalogue.ui.catalogue.series.favorite.SeriesFavoriteFragment

class SearchPagerAdapter(
    private val mContext: Context,
    fragmentManager: FragmentManager,
    private val query: String
): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.movie,
        R.string.tv_series
    )

    override fun getItem(position: Int): Fragment {
        val fragmentMovie = 1
        val fragment: Fragment?
        fragment = if (position == fragmentMovie) {
            FindCatalogueFragment.newInstance(position+1, query)
        } else {
            FindSeriesFragment.newInstance(position, query)
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}