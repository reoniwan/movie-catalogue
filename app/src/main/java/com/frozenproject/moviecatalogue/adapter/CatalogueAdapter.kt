package com.frozenproject.moviecatalogue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.model.CatalogueModel
import kotlinx.android.synthetic.main.item_list_catalogue.view.*

class CatalogueAdapter(
    private val listCatalogue: List<CatalogueModel>,
    private val context: Context,
    private val listener: (CatalogueModel) -> Unit
) : RecyclerView.Adapter<CatalogueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context).inflate(R.layout.item_list_catalogue, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listCatalogue.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listCatalogue[position], listener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(listCatalogue: CatalogueModel, listener: (CatalogueModel) -> Unit) {
            with(itemView) {
                txt_film_name.text = listCatalogue.name
                txt_release_film.text = listCatalogue.date
                txt_rating_film.text = listCatalogue.rating

                Glide.with(itemView)
                    .load(listCatalogue.image)
                    .into(img_movie)

                itemView.setOnClickListener { listener(listCatalogue) }
            }
        }
    }
}