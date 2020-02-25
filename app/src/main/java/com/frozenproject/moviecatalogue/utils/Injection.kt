package com.frozenproject.moviecatalogue.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.db.CatalogueDatabase
import com.frozenproject.moviecatalogue.data.db.LocalRepository
import com.frozenproject.moviecatalogue.data.network.APICatalogueClient
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.data.network.source.RemoteRepository

object Injection {

    private fun provideCacheMovie(context: Context): LocalRepository {
        val database = CatalogueDatabase(context)
        val remoteRepository = provideRemote()
        return LocalRepository(
            database.favoriteMovieDao(),
            database.seriesFavoriteDao(),
            remoteRepository
        )
    }

    private fun provideRemote(): RemoteRepository {
        return RemoteRepository(
            APICatalogueClient.getClient()
        )
    }

    private fun provideTMDBRepository(context: Context): CatalogueRepository {
        return CatalogueRepository(
            provideCacheMovie(context),
            provideRemote()
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideTMDBRepository(context))
    }

    fun provideViewModelFactorySearch(context: Context, query: String): ViewModelProvider.Factory {
        return ViewModelFactorySearch(provideTMDBRepository(context), query)
    }

    fun provideViewModelFactoryDetail(context: Context, id: Int): ViewModelProvider.Factory {
        return ViewModelFactoryDetail(provideTMDBRepository(context), id)
    }

}