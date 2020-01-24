package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.network.*
import com.frozenproject.moviecatalogue.databinding.LayoutCatalogueDetailBinding
import com.frozenproject.moviecatalogue.utils.Injection
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_catalogue_description.*
import kotlinx.android.synthetic.main.layout_catalogue_detail.*
import java.text.NumberFormat
import java.util.*
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
            .get(MovieDetailViewModel::class.java)
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val IS_FAVORITE = "favorite"
    }

    private lateinit var binding: LayoutCatalogueDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.layout_catalogue_detail)
        //Appbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.title = ""

        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Data Movie
        val isFavorite = intent.getBooleanExtra(IS_FAVORITE, false)
        val data = intent.getParcelableExtra<ResultMovie>(EXTRA_MOVIE)

        viewModel.setData(data)

        viewModel.detail.observe(this, Observer {
            binding.model = it

        })

        toggle_favorite.setOnClickListener {
            if (isFavorite){
                toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_red)
                viewModel.deleteFromFavorite(data)
                Toast.makeText(this, "Delete Favorite", Toast.LENGTH_SHORT).show()
            }else{
                toggle_favorite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
                viewModel.addToFavorite(data)
                Toast.makeText(this, "Delete Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}