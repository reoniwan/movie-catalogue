package com.frozenproject.moviecatalogue

import android.app.Application
import com.frozenproject.moviecatalogue.data.database.CatalogueDatabase
import com.frozenproject.moviecatalogue.data.database.MovieCatalogueDao
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepositoryImpl
import com.frozenproject.moviecatalogue.ui.catalogue.movie.MovieViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CatalogueApplication:Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CatalogueApplication))

        bind() from singleton {CatalogueDatabase(instance())}
        bind() from singleton { instance<CatalogueDatabase>().movieCatalogueDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { APICatalogue(instance()) }
        bind<CatalogueNetworkDataSource>() with singleton{ CatalogueNetworkDataSourceImpl(instance())}
        bind<CatalogueRepository>() with singleton { CatalogueRepositoryImpl(instance(), instance()) }
        bind() from provider { MovieViewModelFactory(instance()) }
    }
}