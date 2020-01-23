package com.frozenproject.moviecatalogue.utils

import android.view.View

class CustomOnItemClickListener(
    private val position: Int,
    private val onItemClickCallback: OnItemClickCallBack
): View.OnClickListener {



    interface OnItemClickCallBack {
        fun onItemClicked(view: View, position: Int)
    }

    override fun onClick(v: View) {
        onItemClickCallback.onItemClicked(v, position)
    }

}