package com.frozenproject.moviefavoriteclient.util

import android.database.Cursor

internal interface LoadMovieCallback{
    fun postExecute(cursor: Cursor?)
}