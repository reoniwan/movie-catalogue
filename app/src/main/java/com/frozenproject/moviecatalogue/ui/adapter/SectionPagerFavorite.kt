package com.frozenproject.moviecatalogue.ui.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.ui.catalogue.favorite.FavoriteCatalogueFragment

class SectionPagerFavorite(
    private val mContext: Context,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.movie,
        R.string.tv_series
    )

    override fun getItem(position: Int): Fragment {
        return FavoriteCatalogueFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}