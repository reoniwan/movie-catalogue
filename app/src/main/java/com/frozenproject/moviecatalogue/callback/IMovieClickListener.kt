package com.frozenproject.moviecatalogue.callback

import com.frozenproject.moviecatalogue.model.CatalogueModel

interface IMovieClickListener {
    fun onItemClicked(data: CatalogueModel)
}