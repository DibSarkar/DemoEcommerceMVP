package com.app.demoopencartapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.HomeOffersListBean;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeOffersListAdapter extends RecyclerView.Adapter<HomeOffersListAdapter.ViewHolder>   {

    private final ArrayList<HomeProductsResponse.SliderBean.BannerImageBean> mValues;


    public HomeOffersListener mListener;
    Context mContext;

    public HomeOffersListAdapter(ArrayList<HomeProductsResponse.SliderBean.BannerImageBean> items) {
        mValues = items;

    }

    public interface HomeOffersListener {
        void onItemClick(HomeOffersListBean item, int position);
    }

    public void setAdapterListener(HomeOffersListener mListener) {
        this.mListener = mListener;
    }


    public void loadOffers(List<HomeProductsResponse.SliderBean.BannerImageBean> homeOffersListBeanList) {

        mValues.addAll(homeOffersListBeanList);
        notifyDataSetChanged();
    }


    @Override
    public HomeOffersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_offers, parent, false);
        mContext=parent.getContext();
        return new HomeOffersListAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final HomeOffersListAdapter.ViewHolder holder, final int position) {

        final HomeProductsResponse.SliderBean.BannerImageBean mDataBean = mValues.get(position);
        Glide.with(mContext)
                .load(mDataBean.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.iv_off);


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

        @BindView(R.id.iv_off)
        ImageView iv_off;


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


