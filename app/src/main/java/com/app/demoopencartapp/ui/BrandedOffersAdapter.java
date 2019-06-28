package com.app.demoopencartapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.demoopencartapp.R;

import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrandedOffersAdapter extends RecyclerView.Adapter<BrandedOffersAdapter.ViewHolder>   {

    private final ArrayList<HomeProductsResponse.BrandBean> mValues;


    public BrandedOffersBeanListener mListener;
    Context mContext;

    public BrandedOffersAdapter(ArrayList<HomeProductsResponse.BrandBean> items) {
        mValues = items;

    }

    public interface BrandedOffersBeanListener {
        void onItemClick(HomeProductsResponse.BrandBean item, int position);
    }

    public void setAdapterListener(BrandedOffersBeanListener mListener) {
        this.mListener = mListener;
    }


    public void loadBrands(List<HomeProductsResponse.BrandBean> brandedOffersBeanList) {

        mValues.addAll(brandedOffersBeanList);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_brand_offers, parent, false);
        mContext=parent.getContext();
        return new BrandedOffersAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final BrandedOffersAdapter.ViewHolder holder, final int position) {

        final HomeProductsResponse.BrandBean mDataBean = mValues.get(position);
        Glide.with(mContext)
                .load(mDataBean.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.iv_branded);
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

        @BindView(R.id.iv_branded)
        ImageView iv_branded;


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


