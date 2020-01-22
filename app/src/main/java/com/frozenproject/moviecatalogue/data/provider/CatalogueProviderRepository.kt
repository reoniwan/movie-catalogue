package com.frozenproject.moviecatalogue.data.provider

import com.frozenproject.moviecatalogue.data.db.LocalRepository
import com.frozenproject.moviecatalogue.data.repository.MovieCatalogueRepository

class CatalogueProviderRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: MovieCatalogueRepository
) {

}