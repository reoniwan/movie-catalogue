package com.frozenproject.moviefavoriteclient

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.frozenproject.moviefavoriteclient.adapter.MovieListAdapter
import com.frozenproject.moviefavoriteclient.model.MovieData
import com.frozenproject.moviefavoriteclient.model.constant.Constant
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val listFavoriteMovie = mutableListOf<MovieData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val uriId = Uri.parse(Constant.CONTENT_URI.toString() + "/1")

            val cursor = contentResolver?.query(uriId,
                null,
                null,
                null,
                null
            )
            if (cursor != null) {

                listFavoriteMovie.clear()

                while (cursor.moveToNext()) {
                    val poster = cursor.let { it.getString(it.getColumnIndexOrThrow("poster_path")) }
                    val title = cursor.let { it.getString(it.getColumnIndexOrThrow("title")) }
                    val backDrop = cursor.let { it.getString(it.getColumnIndexOrThrow("backdrop_path")) }
                    val date = cursor.let { it.getString(it.getColumnIndexOrThrow("release_date")) }

                    listFavoriteMovie.add(MovieData(poster,date,title,backDrop))
                }

                runOnUiThread {

                    recycler_favourite_client.layoutManager = LinearLayoutManager(this@MainActivity)
                    recycler_favourite_client.adapter = MovieListAdapter(this@MainActivity, listFavoriteMovie)

                }
                cursor.close()
            }

        }

    }
}
