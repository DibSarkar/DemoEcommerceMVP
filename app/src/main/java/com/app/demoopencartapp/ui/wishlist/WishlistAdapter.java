package com.app.demoopencartapp.ui.wishlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.utils.GlideApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>   {

    private final ArrayList<CategoriesProductsResponse.ProductBean> mValues;


    public WishlistProductListener mListener;
    Context mContext;
    Activity activity;
    int status;

    public WishlistAdapter(ArrayList<CategoriesProductsResponse.ProductBean> items) {
        mValues = items;


    }

    public interface WishlistProductListener {
        void onItemClick(CategoriesProductsResponse.ProductBean item, int position);
        void onWishDeleteSelected(CategoriesProductsResponse.ProductBean item, int position);
        void onAddtoCart(CategoriesProductsResponse.ProductBean item, int position, String quantity);

    }

    public void setAdapterListener(WishlistProductListener mListener) {
        this.mListener = mListener;
    }


    public void loadWishlistProducts(List<CategoriesProductsResponse.ProductBean> productListBeanList) {

        mValues.addAll(productListBeanList);
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_wishlist, null);
        mContext=parent.getContext();
        return new ViewHolder(layoutView);
    }



    @Override
    public void onBindViewHolder(final WishlistAdapter.ViewHolder holder, final int position) {


        final CategoriesProductsResponse.ProductBean mDataBean = mValues.get(position);
        holder.tv_pro_name.setText(mDataBean.getName());
        holder.tv_manufacturer.setText("By - "+mDataBean.getManufacturer());
        if(!mDataBean.getSpecial().equals(""))
        {
            holder.tv_product_price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(mDataBean.getSpecial()))));
            holder.iv_offer.setVisibility(View.VISIBLE);
            holder.tv_offer.setVisibility(View.VISIBLE);
            holder.tv_product_old__price.setVisibility(View.VISIBLE);
            holder.tv_product_old__price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(mDataBean.getPrice()))));
            holder.tv_product_old__price.setPaintFlags( holder.tv_product_old__price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_savings.setVisibility(View.VISIBLE);
            double savings_amt = (Double.parseDouble(mDataBean.getPrice())-Double.parseDouble(mDataBean.getSpecial()));
            holder.tv_savings.setText("You save "+'\u20B9'+" "+String.valueOf(Math.round(savings_amt)));
            double offer = Math.round(((Double.parseDouble(mDataBean.getPrice())-Double.parseDouble(mDataBean.getSpecial()))/Double.parseDouble(mDataBean.getPrice()))*100);
            DecimalFormat df = new DecimalFormat("###.#");
            holder.tv_offer.setText(df.format(offer)+"%"+"\n"+"OFF");


        }
        else {
            holder.tv_product_price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(mDataBean.getPrice()))));
            holder.tv_product_old__price.setVisibility(View.GONE);
            holder.tv_savings.setVisibility(View.INVISIBLE);
            holder.iv_offer.setVisibility(View.INVISIBLE);
            holder.tv_offer.setVisibility(View.INVISIBLE);
        }

        GlideApp.with(mContext)
                .load(mDataBean.getThumb())
                .placeholder(R.drawable.no_image)
                .centerCrop()
                .into(holder.iv_product);


        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mDataBean.getOptions().isEmpty()) {
                    mListener.onAddtoCart(mValues.get(position), position, mDataBean.getMinimum());
                }
                else {
                    mListener.onItemClick(mValues.get(position), position);
                }

            }
        });


        holder.iv_delete_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onWishDeleteSelected(mValues.get(position), position);
            }
        });



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

        @BindView(R.id.tv_product_old__price)
        TextView tv_product_old__price;

        @BindView(R.id.tv_product_price)
        TextView tv_product_price;

        @BindView(R.id.tv_pro_name)
        TextView tv_pro_name;

        @BindView(R.id.tv_manufacturer)
        TextView tv_manufacturer;

        @BindView(R.id.tv_savings)
        TextView tv_savings;

        @BindView(R.id.tv_offer)
        TextView tv_offer;

        @BindView(R.id.iv_product)
        ImageView iv_product;

        @BindView(R.id.iv_offer)
        ImageView iv_offer;

        @BindView(R.id.iv_delete_wish)
        ImageView iv_delete_wish;

        @BindView(R.id.btn_add)
        Button btn_add;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mValues.get(getLayoutPosition()), getLayoutPosition());
                }
            });

        }
    }
}

