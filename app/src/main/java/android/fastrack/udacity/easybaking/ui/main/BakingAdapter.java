package android.fastrack.udacity.easybaking.ui.main;

import android.content.Context;
import android.content.Intent;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.fastrack.udacity.easybaking.model.BakingConstanta;
import android.fastrack.udacity.easybaking.ui.detail.DetailActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by winzaldi on 8/28/17.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.BakingViewHolder> {
    private Context mContext;
    private List<Baking> listOfBaking;
    private LayoutInflater mInflater;


    public BakingAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.listOfBaking = new ArrayList<>();
    }

    @Override
    public BakingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_baking, parent, false);
        final BakingViewHolder viewHolder = new BakingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BakingViewHolder holder, int position) {
        final Baking baking = listOfBaking.get(position);
        holder.getTitle().setText(baking.getName());
        if(baking.getImage()!=null && baking.getImage().length() > 5){
            Picasso.with(mContext)
                    .load(baking.getImage())
                    .placeholder(R.drawable.nothumbnail)
                    .error(R.drawable.nothumbnail)
                    .into(holder.imgContent);
        }else {
            holder.getImgContent().setBackgroundResource(R.drawable.nothumbnail);
        }

        holder.imgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle data = new Bundle();
                data.putParcelable(BakingConstanta.KEY_BAKING_OBJECT, Parcels.wrap(baking));
                intent.putExtras(data);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfBaking.size();
    }

    public void setListOfBaking(List<Baking> list) {
        this.listOfBaking.clear();
        this.listOfBaking.addAll(list);
        notifyDataSetChanged();
    }

    //View Holder
    public class BakingViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView imgContent;

        public BakingViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            imgContent = (ImageView) itemView.findViewById(R.id.img_content);
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImgContent() {
            return imgContent;
        }
    }
}
