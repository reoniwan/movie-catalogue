package com.frozenproject.moviecatalogue.callback

import androidx.recyclerview.widget.DiffUtil
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie


class MovieCallback : DiffUtil.ItemCallback<ResultMovie>() {

    override fun areItemsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean {
        return oldItem.idMovie == newItem.idMovie||
                oldItem.title == newItem.title

    }

    override fun areContentsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean {
        return oldItem == newItem
    }

}