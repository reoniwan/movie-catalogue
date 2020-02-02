package com.frozenproject.moviecatalogue.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries

@Database(
    entities = [ResultMovie::class, ResultSeries::class],
    version = 1
)
abstract class CatalogueDatabase : RoomDatabase() {
    abstract val favoriteMovieDao: MovieFavoriteDao
    abstract val seriesFavoriteDao: SeriesFavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: CatalogueDatabase? = null

        fun getDatabase(context: Context): CatalogueDatabase {
            val temptInstance = INSTANCE
            if (temptInstance != null) {
                return temptInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatalogueDatabase::class.java,
                    "Favorite_Database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}