package com.frozenproject.moviecatalogue.data.database

import android.content.Context
import androidx.room.*
import com.frozenproject.moviecatalogue.data.database.entity.ResultMovie


@Database(
    entities = [ResultMovie::class],
    version = 1
)

abstract class CatalogueDatabase:RoomDatabase(){
    abstract fun movieCatalogueDao():MovieCatalogueDao

    companion object{
        @Volatile private var instance: CatalogueDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            (instance ?: buildDatabase(context).also{ instance = it })
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CatalogueDatabase::class.java, "catalogueEntries.db")
                .build()
    }
}

