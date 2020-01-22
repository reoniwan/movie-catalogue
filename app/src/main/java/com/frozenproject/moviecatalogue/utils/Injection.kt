package com.frozenproject.moviecatalogue.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.db.CatalogueDatabase
import com.frozenproject.moviecatalogue.data.db.LocalRepository
import com.frozenproject.moviecatalogue.data.network.APICatalogueClient
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.data.repository.remote.MovieRemoteRepository
import java.util.concurrent.Executors

object Injection {

    private fun provideCacheMovie(context: Context): LocalRepository{
        val database = CatalogueDatabase.getDatabase(context)
        return LocalRepository(database.favoriteMovieDao, Executors.newSingleThreadExecutor())
    }

    private fun provideRemote(): MovieRemoteRepository{
        return MovieRemoteRepository(APICatalogueClient.getClient())
    }

    private fun provideTMDBRepository(context: Context): MovieCatalogueRepository {
        return MovieCatalogueRepository(
            provideCacheMovie(context),
            provideRemote()
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory{
        return ViewModelFactory(provideTMDBRepository(context))
    }

}