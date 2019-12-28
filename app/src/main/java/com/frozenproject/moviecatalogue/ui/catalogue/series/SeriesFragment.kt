package com.frozenproject.moviecatalogue.ui.catalogue.series


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.frozenproject.moviecatalogue.R

/**
 * A simple [Fragment] subclass.
 */
class SeriesFragment : Fragment() {


    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): SeriesFragment {
            val fragment =
                SeriesFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_series, container, false)

        return root
    }

}
