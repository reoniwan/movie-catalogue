package com.frozenproject.moviecatalogue.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries

@Database(
    entities = [ResultMovie::class, ResultSeries::class],
    version = 1,
    exportSchema = false
)
abstract class CatalogueDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): MovieFavoriteDao
    abstract fun seriesFavoriteDao(): SeriesFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: CatalogueDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: getDatabase(context).also { INSTANCE = it }
        }

        fun getDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CatalogueDatabase::class.java,
            "Favorite_Database"
        ).build()
    }
}