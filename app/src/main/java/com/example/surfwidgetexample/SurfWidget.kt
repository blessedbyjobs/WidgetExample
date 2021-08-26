package com.example.surfwidgetexample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri


class SurfWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.surf_widget)

        val widgetFact = InfoRepository(context).getData(appWidgetId)

        views.setTextViewText(R.id.title_tv, widgetFact.title)
        views.setTextViewText(R.id.text_tv, widgetFact.text)
        views.setTextViewText(R.id.opinion_tv, widgetFact.opinion)

        val intentUpdate = Intent(context, SurfWidget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        }

        val intentOpenLink = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse(widgetFact.url)
        }

        val pendingUpdate = PendingIntent.getBroadcast(
            context,
            appWidgetId,
            intentUpdate,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pendingOpenLink = PendingIntent.getActivity(
            context,
            0,
            intentOpenLink,
            PendingIntent.FLAG_IMMUTABLE
        )

        views.setOnClickPendingIntent(R.id.next_btn, pendingUpdate)
        views.setOnClickPendingIntent(R.id.open_in_browser_btn, pendingOpenLink)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}