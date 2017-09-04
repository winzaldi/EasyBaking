package android.fastrack.udacity.easybaking.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.fastrack.udacity.easybaking.model.BakingConstanta;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by winzaldi on 9/2/17.
 */

public final class EasyBakingWidgetManager {

    private  static Gson gson = new Gson();

    public static Baking getBaking(Context mContext){
        Baking baking = null;
        SharedPreferences  preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String strBaking =  preferences.getString(BakingConstanta.PREF_BAKING,null);
        baking = gson.fromJson(strBaking,Baking.class);
        return baking;

    }
    private static void updateBaking(Context mContext,Baking baking){
         String strBaking  = gson.toJson(baking);
         PreferenceManager.getDefaultSharedPreferences(mContext)
                 .edit().putString(BakingConstanta.PREF_BAKING,strBaking).commit();
    }

   public  static  void updateBakingWidget(Context mContext,Baking baking){
          updateBaking(mContext,baking);
       Intent intent = new Intent(mContext,EasyBakingWidgetProvider.class);
       intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
       AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
       ComponentName componentName = new ComponentName(mContext,EasyBakingWidgetProvider.class);
       int[] appWigdetIds  = appWidgetManager.getAppWidgetIds(componentName);
       intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWigdetIds);
       mContext.sendBroadcast(intent);
       appWidgetManager.notifyAppWidgetViewDataChanged(appWigdetIds, R.id.lv_widget);

   }


}
