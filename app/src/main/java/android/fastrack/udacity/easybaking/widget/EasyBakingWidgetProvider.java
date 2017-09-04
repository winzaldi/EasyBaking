package android.fastrack.udacity.easybaking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class EasyBakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        Baking baking =  EasyBakingWidgetManager.getBaking(context);
        String widgetText = (baking!=null) ?  baking.getName() : context.getString(R.string.widget_no_recipe);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.easy_baking_widget_provider);
        views.setTextViewText(R.id.tv_widget, widgetText);
        Intent intent =  new Intent(context,EasyBakingWidgetRemoteViewService.class);
        views.setRemoteAdapter(R.id.lv_widget, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

