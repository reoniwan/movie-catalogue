package com.frozenproject.moviecatalogue.internal

import com.frozenproject.moviecatalogue.ui.model.SeriesCatalogueModel

interface ISeriesClickListener {
    fun onItemClicked(data: SeriesCatalogueModel)
}