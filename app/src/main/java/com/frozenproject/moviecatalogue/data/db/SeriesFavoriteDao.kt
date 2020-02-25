package com.frozenproject.moviecatalogue.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries

@Dao
interface SeriesFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ResultSeries)

    @Query("select * from favorite_series")
    fun getAllFavoriteSeries(): LiveData<List<ResultSeries>>

    @Query("select * from favorite_series where isFavorite = 1")
    fun selectByFavoriteForWidget(): List<ResultSeries>

    @Update
    fun update(series: ResultSeries)

    @Query("select * from favorite_series where id = :seriesId")
    fun selectById(seriesId: Int): LiveData<ResultSeries>

    @Query("select count(id) from favorite_series where id = :seriesId")
    fun countById(seriesId: Int): Int

    //Cursor
    @Query("select * from favorite_series")
    fun selectAllForCursor(): Cursor

    @Query("select * from favorite_series where isFavorite = 1")
    fun selectByFavoriteForCursor(): Cursor
}