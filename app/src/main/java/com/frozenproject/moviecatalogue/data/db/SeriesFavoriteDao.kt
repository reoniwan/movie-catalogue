package com.frozenproject.moviecatalogue.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries

@Dao
interface SeriesFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ResultSeries)

    @Delete
    fun delete(data: ResultSeries)

    @Query("select * from favorite_series")
    fun getAllFavoriteSeries(): DataSource.Factory<Int, ResultSeries>


    @Query("select * from favorite_series")
    fun selectByFavoriteForWidget(): List<ResultSeries>
}