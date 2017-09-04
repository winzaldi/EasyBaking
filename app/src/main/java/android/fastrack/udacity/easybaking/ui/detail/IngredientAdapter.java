package android.fastrack.udacity.easybaking.ui.detail;

import android.content.Context;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.fastrack.udacity.easybaking.model.Ingredients;
import android.fastrack.udacity.easybaking.ui.main.BakingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winzaldi on 8/30/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private ArrayList<Ingredients> listOfIngredients;
    private LayoutInflater mInflater;

    public IngredientAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.listOfIngredients = new ArrayList<>();
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_ingredient, parent, false);
        final IngredientAdapter.IngredientViewHolder viewHolder = new IngredientAdapter.IngredientViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        final Ingredients ingredients = listOfIngredients.get(position);
        holder.tvIngredient.setText(ingredients.getIngredient());
        String qMesaurement =  ingredients.getQuantity() + " " + ingredients.getMeasure();
        holder.tvMeasurement.setText(qMesaurement);


    }

    public void setListOfIngredients(ArrayList<Ingredients> listOfIngredients) {
        this.listOfIngredients.clear();
        this.listOfIngredients = listOfIngredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIngredient,tvMeasurement;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            tvIngredient = (TextView) itemView.findViewById(R.id.tv_ingredient);
            tvMeasurement = (TextView) itemView.findViewById(R.id.tv_measurement);
        }


    }
}
