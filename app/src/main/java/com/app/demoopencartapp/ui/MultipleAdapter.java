package com.app.demoopencartapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.demoopencartapp.R;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenevo on 28-06-2018.
 */

public class MultipleAdapter extends RecyclerView.Adapter<MultipleAdapter.ViewHolder>   {

    private final ArrayList<Integer> mValues;


    public MultipleImagesListener mListener;
    Context mContext;

    public MultipleAdapter(ArrayList<Integer> items) {
        mValues = items;

    }

    public interface MultipleImagesListener {
        void onItemClick(ArrayList<Integer> item, int position);
    }

    public void setAdapterListener(MultipleImagesListener mListener) {
        this.mListener = mListener;
    }


    public void loadImages(ArrayList<Integer> multipleList) {

        mValues.addAll(multipleList);
        notifyDataSetChanged();
    }


    @Override
    public MultipleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_img, parent, false);
        mContext=parent.getContext();
        return new MultipleAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final MultipleAdapter.ViewHolder holder, final int position) {
        holder.iv_mul_image.setImageResource((Integer) mValues.get(position));

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_mul_image)
        ImageView iv_mul_image;



        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
          /*  view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mValues.get(getLayoutPosition()), getLayoutPosition());
                }
            });*/

        }
    }
}


