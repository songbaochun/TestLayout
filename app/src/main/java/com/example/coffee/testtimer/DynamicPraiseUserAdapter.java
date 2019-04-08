package com.example.coffee.testtimer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by coffee on 2019/3/21.
 */

public class DynamicPraiseUserAdapter extends RecyclerView.Adapter<DynamicPraiseUserAdapter.DynamicPraiseUserHolder> {
    private List<FunctionalModuleEntity> praiseUserData;
    private Context context;

    public DynamicPraiseUserAdapter(Context context, List<FunctionalModuleEntity> praiseUserData) {
        this.praiseUserData = praiseUserData;
        this.context = context;
    }

    @Override
    public DynamicPraiseUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_home_page_modular, parent, false);
        return new DynamicPraiseUserHolder(inflate);
    }

    @Override
    public int getItemCount() {
        return praiseUserData.size();
    }

    @Override
    public void onBindViewHolder(DynamicPraiseUserHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(praiseUserData.get(position).modularIcon)
                .into(holder.im_modular);
        holder.tv_modular_name.setText(praiseUserData.get(position).modularName);
    }

    class DynamicPraiseUserHolder extends RecyclerView.ViewHolder {
        private TextView tv_modular_name;
        private ImageView im_modular;

        public DynamicPraiseUserHolder(View itemView) {
            super(itemView);
            tv_modular_name = itemView.findViewById(R.id.tv_modular_name);
            im_modular = itemView.findViewById(R.id.im_modular);
        }
    }
}
