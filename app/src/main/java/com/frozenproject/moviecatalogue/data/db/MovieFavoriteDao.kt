package com.frozenproject.moviecatalogue.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.*
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie

@Dao
interface MovieFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ResultMovie)

    @Delete
    fun delete(data: ResultMovie)

    @Query("select * from favorite_movie")
    fun getAllFavoriteMovie(): DataSource.Factory<Int, ResultMovie>

    @Query("select * from favorite_movie")
    fun selectByFavoriteForWidget(): List<ResultMovie>

}