package com.frozenproject.moviecatalogue.ui.catalogue.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.frozenproject.moviecatalogue.R

/**
 * Implementation of App Widget functionality.
 */
class FavoriteBannerWidget : AppWidgetProvider() {

    companion object{
        const val TOAST_ACTION = "TOAST_ACTION"
        const val EXTRA_ITEM = "EXTRA_ITEM"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null){
            val viewIndex = intent.getStringExtra(EXTRA_ITEM)
            Toast.makeText(context, viewIndex, Toast.LENGTH_SHORT).show()
        }
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent = Intent(context, StackWidgetService::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

    val views = RemoteViews(context.packageName, R.layout.favorite_banner_widget)
    views.setRemoteAdapter(R.id.stack_view_widget, intent)
    views.setEmptyView(R.id.stack_view_widget, R.id.empty_view)

    val toastIntent = Intent(context, FavoriteBannerWidget::class.java)
    toastIntent.action = FavoriteBannerWidget.TOAST_ACTION
    toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

    intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

    val toastPendingIntent =
        PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    views.setPendingIntentTemplate(R.id.stack_view_widget, toastPendingIntent)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}