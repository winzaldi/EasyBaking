package android.fastrack.udacity.easybaking.widget;

import android.content.Context;
import android.content.Intent;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.fastrack.udacity.easybaking.model.Ingredients;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by winzaldi on 9/2/17.
 */

public class EasyBakingWidgetRemoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new EasyBakingWidgetRemoteViewFactory(getApplicationContext());
    }
}


class EasyBakingWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    public static final String TAG = EasyBakingWidgetRemoteViewFactory.class.getSimpleName();

    Context mContext;
    Baking baking;

    public EasyBakingWidgetRemoteViewFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        baking = EasyBakingWidgetManager.getBaking(mContext);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (baking!=null){
            return baking.getIngredients().size();
        }
        return  0;

    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_ingredient);
        Ingredients ingredient = baking.getIngredients().get(position);
        Log.i(TAG, "getViewAt: " + ingredient.toString());
        if (ingredient != null) {
            views.setTextViewText(R.id.tv_recipe_ingredient, ingredient.getIngredient());
            views.setTextViewText(R.id.tv_recipe_ingredient_measurement,
                    mContext.getString(R.string.quantity_measurement_format,
                            ingredient.getQuantity(), ingredient.getMeasure()));
        }



//        // Fill in the onClick PendingIntent Template
//        Bundle extras = new Bundle();
//        extras.putLong(BakingConstanta.KEY_BAKING_ID, Long.valueOf(baking.getId()));
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//        //views.setOnClickFillInIntent(R.id.lv_widget, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}