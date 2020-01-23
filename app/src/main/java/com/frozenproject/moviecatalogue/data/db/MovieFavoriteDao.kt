package com.frozenproject.moviecatalogue.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie

@Dao
interface MovieFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: MovieDetail)

    @Delete
    fun delete(data: MovieDetail)

    @Query("select * from favorite_movie where id = :id")
    fun getAllFavoriteMovie(id: Int): LiveData<MovieDetail>

}