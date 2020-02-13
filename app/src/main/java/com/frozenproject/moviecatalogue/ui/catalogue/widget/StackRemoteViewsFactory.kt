package com.frozenproject.moviecatalogue.ui.catalogue.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.frozenproject.moviecatalogue.data.db.CatalogueDatabase
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import androidx.paging.DataSource
import com.bumptech.glide.Glide
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import java.util.concurrent.ExecutionException

class StackRemoteViewsFactory(
    private val mContext: Context
): RemoteViewsService.RemoteViewsFactory {

    private val widgetItems = ArrayList<String>()
    private val widgetsItemsName = ArrayList<String>()
    private lateinit var movies: List<ResultMovie>
    private lateinit var series: List<ResultSeries>
    private lateinit var database: CatalogueDatabase

    override fun onCreate() {
        database = CatalogueDatabase.getDatabase(mContext)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        movies = database.favoriteMovieDao.selectByFavoriteForWidget()
        series = database.seriesFavoriteDao.selectByFavoriteForWidget()

        for (entryMovie in movies){
            widgetItems.add(entryMovie.backdrop)
            widgetsItemsName.add(entryMovie.title)
        }

        for (entrySeries in series){
            widgetItems.add(entrySeries.backdropSeries)
            widgetsItemsName.add(entrySeries.name)
        }

        Binder.restoreCallingIdentity(identityToken)

    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val item = widgetItems[position]
        val itemName = widgetsItemsName[position]

        val backdropPath = POSTER_BASE_URL + item

        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        var bitmap: Bitmap? = null
        try {
            bitmap = Glide.with(mContext)
                .asBitmap()
                .load(backdropPath)
                .submit()
                .get()
        } catch (e: InterruptedException){
            e.printStackTrace()
        } catch (e: ExecutionException){
            e.printStackTrace()
        }

        rv.setImageViewBitmap(R.id.img_widget_backdrop, bitmap)

        val extras = bundleOf(
            FavoriteBannerWidget.EXTRA_ITEM to itemName
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.img_widget_backdrop, fillInIntent)
        return rv
    }

    override fun getCount(): Int = widgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}

}