package com.frozenproject.moviecatalogue.callback

import androidx.recyclerview.widget.DiffUtil
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries

class SeriesCallback : DiffUtil.ItemCallback<ResultSeries>() {
    override fun areItemsTheSame(oldItem: ResultSeries, newItem: ResultSeries): Boolean {
        return oldItem.idSeries == newItem.idSeries ||
                oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ResultSeries, newItem: ResultSeries): Boolean {
        return oldItem == newItem
    }

}