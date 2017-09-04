package android.fastrack.udacity.easybaking.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.BakingConstanta;
import android.fastrack.udacity.easybaking.model.Steps;
import android.fastrack.udacity.easybaking.ui.step.ItemStepsFragment;
import android.fastrack.udacity.easybaking.ui.step.ReceipStepActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by winzaldi on 8/30/17.
 */

public class DetailStepAdapter extends RecyclerView.Adapter<DetailStepAdapter.DetailStepViewHolder> {

    private Context mContext;
    private ArrayList<Steps> listOfSteps;
    private LayoutInflater mInflater;
    private FragmentManager fragmentManager;

    public DetailStepAdapter(Context mContext, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.listOfSteps = new ArrayList<Steps>();
        this.fragmentManager = fragmentManager;
    }

    @Override
    public DetailStepAdapter.DetailStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_steps, parent, false);
        final DetailStepAdapter.DetailStepViewHolder viewHolder = new DetailStepAdapter.DetailStepViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailStepAdapter.DetailStepViewHolder holder, final int position) {
        final Steps steps = listOfSteps.get(position);
        holder.tvStep.setText(steps.getShortDescription());
        if (steps.getThumbnailURL() != null
                && steps.getThumbnailURL().length() > 5) {
            Picasso.with(mContext)
                    .load(steps.getThumbnailURL())
                    .placeholder(R.drawable.nothumbnail)
                    .error(R.drawable.nothumbnail)
                    .into(holder.imgImage);
        }else{
            holder.imgImage.setImageResource(R.drawable.nothumbnail);
        }

        holder.imgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DetailActivity.mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(BakingConstanta.KEY_STEPS_OBJECT, Parcels.wrap(steps));
                    ItemStepsFragment fragment = new ItemStepsFragment();
                    fragment.setArguments(arguments);
                    fragmentManager.beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(mContext, ReceipStepActivity.class);
                    intent.putExtra(BakingConstanta.KEY_LIST_STEPS, Parcels.wrap(listOfSteps));
                    intent.putExtra(BakingConstanta.KEY_POSITION_STEP, position);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfSteps.size();
    }

    public void setListOfSteps(ArrayList<Steps> listOfSteps) {
        this.listOfSteps.clear();
        this.listOfSteps = listOfSteps;
        notifyDataSetChanged();
    }

    public class DetailStepViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStep;
        private ImageView imgImage;

        public DetailStepViewHolder(View itemView) {
            super(itemView);
            tvStep = (TextView) itemView.findViewById(R.id.tv_title_step);
            imgImage = (ImageView) itemView.findViewById(R.id.img_content_step);
        }
    }
}
