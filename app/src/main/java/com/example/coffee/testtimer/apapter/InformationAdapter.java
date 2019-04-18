package com.example.coffee.testtimer.apapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.coffee.testtimer.MainActivity;
import com.example.coffee.testtimer.R;
import com.example.coffee.testtimer.entity.InformationEntity;

import java.util.List;

/**
 * Created by coffee on 2019/4/11.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationHolder> {
    private Context context;
    private List<InformationEntity> data;
    private int layout;

    public InformationAdapter(Context context, List<InformationEntity> data, int layout) {
        this.context = context;
        this.data = data;
        this.layout = layout;
    }

    @Override
    public InformationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InformationHolder(LayoutInflater.from(context).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final InformationHolder holder, int position) {
//        InformationEntity informationEntity = data.get(position);
//        holder.tv_information_content.setText(informationEntity.content);
//        holder.tv_information_title.setText(informationEntity.title);
//        Glide.with(context)
//                .asBitmap()
//                .load(informationEntity.imgUrl)
//                .into(new BitmapImageViewTarget(holder.im_information) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        super.setResource(resource);
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCornerRadius(20); //设置圆角弧度
//                        holder.im_information.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class InformationHolder extends RecyclerView.ViewHolder {
        ImageView im_information;
        TextView tv_information_title;
        TextView tv_information_content;

        public InformationHolder(View itemView) {
            super(itemView);
//            im_information = itemView.findViewById(R.id.im_information);
//            tv_information_title = itemView.findViewById(R.id.tv_information_title);
//            tv_information_content = itemView.findViewById(R.id.tv_information_content);
        }
    }
}
