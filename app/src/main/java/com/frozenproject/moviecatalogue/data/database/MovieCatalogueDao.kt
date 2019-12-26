package com.frozenproject.moviecatalogue.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frozenproject.moviecatalogue.data.database.entity.MOVIE_ID
import com.frozenproject.moviecatalogue.data.database.entity.ResultMovie
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.MovieSpesificCatalogueEntry

@Dao
interface MovieCatalogueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieCatalogueEntries: List<ResultMovie>)

    @Query("select * from MovieCatalogue where id == idMovie")
    fun getCatalogueResult(): LiveData<List<MovieSpesificCatalogueEntry>>


}