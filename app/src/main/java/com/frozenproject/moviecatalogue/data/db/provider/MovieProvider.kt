package com.frozenproject.moviecatalogue.data.db.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie

class MovieProvider : ContentProvider() {

    companion object{
        private const val AUTHORITY = "com.frozenproject.moviecatalogue.data.db.provider"
        private val uriMatch = UriMatcher(UriMatcher.NO_MATCH)

        private const val CODE_FAVORITE_DIR = 1
        private const val CODE_FAVORITE_ITEM = 2

        init {
            uriMatch.addURI(AUTHORITY, "favorite_movie", CODE_FAVORITE_DIR)
            uriMatch.addURI(AUTHORITY, "favorite_movie/#", CODE_FAVORITE_ITEM)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}