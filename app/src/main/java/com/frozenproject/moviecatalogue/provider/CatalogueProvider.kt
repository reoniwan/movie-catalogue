package com.frozenproject.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.frozenproject.moviecatalogue.data.db.CatalogueDatabase
import com.frozenproject.moviecatalogue.data.db.MovieFavoriteDao
import com.frozenproject.moviecatalogue.data.db.SeriesFavoriteDao
import com.frozenproject.moviecatalogue.data.db.movie.TABLE_MOVIE
import com.frozenproject.moviecatalogue.data.db.series.TABLE_SERIES

class CatalogueProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.frozenproject.moviecatalogue.provider"
        lateinit var movieDao: MovieFavoriteDao
        lateinit var seriesDao: SeriesFavoriteDao

        val URI_MOVIE: Uri = Uri.parse("content://$AUTHORITY/$TABLE_MOVIE")
        val URI_SERIES: Uri = Uri.parse("content://$AUTHORITY/$TABLE_SERIES")

        val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        const val CODE_MOVIE_DIR = 1
        const val CODE_MOVIE_ITEM = 2

        const val CODE_SERIES_DIR = 3
        const val CODE_SERIES_ITEM = 4

        init {
            MATCHER.addURI(AUTHORITY, TABLE_MOVIE, CODE_MOVIE_DIR)
            MATCHER.addURI(AUTHORITY, "${TABLE_MOVIE}/#", CODE_MOVIE_ITEM)

            MATCHER.addURI(AUTHORITY, TABLE_SERIES, CODE_SERIES_DIR)
            MATCHER.addURI(AUTHORITY, "${TABLE_SERIES}/#", CODE_SERIES_ITEM)
        }
    }

    override fun onCreate(): Boolean {
        movieDao = CatalogueDatabase(context as Context).favoriteMovieDao()
        seriesDao = CatalogueDatabase(context as Context).seriesFavoriteDao()
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        return when (MATCHER.match(uri)) {
            CODE_MOVIE_DIR -> movieDao.selectAllForCursor()
            CODE_MOVIE_ITEM -> movieDao.selectByFavoriteForCursor()

            CODE_SERIES_DIR -> seriesDao.selectAllForCursor()
            CODE_SERIES_ITEM -> seriesDao.selectByFavoriteForCursor()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0
}
