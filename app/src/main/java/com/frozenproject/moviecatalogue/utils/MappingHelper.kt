package com.frozenproject.moviecatalogue.utils

import android.database.Cursor
import com.frozenproject.moviecatalogue.data.db.movie.*
import com.frozenproject.moviecatalogue.data.db.series.*

object MappingHelper {

    fun mapCursorToListMovie(cursor: Cursor): List<ResultMovie> {
        val movieList = ArrayList<ResultMovie>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val posterPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER))
            val backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKDROP))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val rating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING))
            val revenue = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_REVENUE))
            val budget = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET))
            val overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW))
            val dateRelease = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val isFavorite =
                cursor.getInt(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAV)))

            movieList.add(
                ResultMovie(
                    id,
                    overview,
                    posterPath,
                    dateRelease,
                    title,
                    rating,
                    backdropPath,
                    budget,
                    revenue,
                    isFavorite
                )
            )
        }

        return movieList
    }

    fun mapCursorToListSeries(cursor: Cursor): List<ResultSeries> {
        val seriesList = ArrayList<ResultSeries>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_SERIES))
            val posterPath =
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_SERIES))
            val backdropPath =
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKDROP_SERIES))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE_SERIES))
            val voteAverage =
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING_SERIES))
            val overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW_SERIES))
            val episode = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EPISODE))
            val dateReleaseFirst = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_FIRST))
            val dateReleaseLast = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_LAST))
            val isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAV_SERIES))

            seriesList.add(
                ResultSeries(
                    id,
                    overview,
                    posterPath,
                    dateReleaseFirst,
                    name,
                    voteAverage,
                    backdropPath,
                    episode,
                    dateReleaseLast,
                    isFavorite
                )
            )
        }

        return seriesList
    }
}