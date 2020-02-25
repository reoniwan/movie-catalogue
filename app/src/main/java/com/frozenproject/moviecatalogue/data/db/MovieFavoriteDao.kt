package com.frozenproject.moviecatalogue.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie

@Dao
interface MovieFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ResultMovie)

    @Query("select * from favorite_movie where id= :movieId")
    fun getMovieById(movieId: Int): LiveData<ResultMovie>

    @Query("select * from favorite_movie where is_favorite = 1")
    fun selectByFavoriteForWidget(): List<ResultMovie>

    @Query("select * from favorite_movie")
    fun selectAllForCursor(): Cursor

    @Query("select * from favorite_movie where is_favorite = 1")
    fun selectByFavoriteForCursor(): Cursor

    @Update
    fun update(data: ResultMovie)

    @Query("select count(id) from favorite_movie where id = :idMovie")
    fun countById(idMovie: Int): Int

}