package com.frozenproject.moviecatalogue.data.db

import androidx.paging.DataSource
import androidx.room.*
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie

@Dao
interface MovieFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ResultMovie)

    @Delete
    fun delete(data: ResultMovie)

    @Query("select * from favorite_movie where title = :title")
    fun getAllFavoriteMovie(title: String): DataSource.Factory<Int, ResultMovie>

}