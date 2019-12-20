package com.frozenproject.moviecatalogue.callback

import com.frozenproject.moviecatalogue.model.SeriesCatalogueModel

interface ISeriesClickListener {
    fun onItemClicked(data: SeriesCatalogueModel)
}