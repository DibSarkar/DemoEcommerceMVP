package com.app.demoopencartapp.ui.productList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demoopencartapp.R;

import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.utils.GlideApp;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>   {

    private final List<CategoriesProductsResponse.ProductBean> mValues;


    public ProductListListener mListener;
    Context mContext;
    Activity activity;
    int status;

    public ProductListAdapter(Activity activity, Context context, List<CategoriesProductsResponse.ProductBean> items, int status) {
        mValues = items;
        this.mContext = context;
        this.activity=activity;
        this.status=status;

    }

    public interface ProductListListener {
        void onItemClick(CategoriesProductsResponse.ProductBean item, int position);
        void onWishSelected(CategoriesProductsResponse.ProductBean item, int position);
        void onAddtoCart(CategoriesProductsResponse.ProductBean item, int position, String quantity);

    }

    public void setAdapterListener(ProductListListener mListener) {
        this.mListener = mListener;
    }


    public void loadProducts(List<CategoriesProductsResponse.ProductBean> productListBeanList) {

        mValues.addAll(productListBeanList);
        notifyDataSetChanged();
    }

    public void changeWish(CategoriesProductsResponse.ProductBean productBean,int pos)
    {
        notifyItemChanged(pos,productBean);
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = null;
        if(this.status==1) {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product2, null);
        }
        else {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product, null);
        }

        return new ProductListAdapter.ViewHolder(layoutView);
    }



    @Override
    public void onBindViewHolder(final ProductListAdapter.ViewHolder holder, final int position) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        final CategoriesProductsResponse.ProductBean mDataBean = mValues.get(position);
        holder.tv_pro_name.setText(mDataBean.getName());
        holder.tv_manufacturer.setText("By - "+mDataBean.getManufacturer());
        if(!mDataBean.getSpecial().equals("0.00"))
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


       /* holder.np_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDataBean.getOptions().isEmpty())
                {
                    mListener.onItemClick(mValues.get(position), position);
                    holder.np_count.setAddActionEnabled(false);
                    holder.np_count.setActionEnabled(ActionEnum.INCREMENT,false);
                    holder.np_count.setActionEnabled(ActionEnum.DECREMENT,false);

                }

            }
        });

        holder.np_count.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                if(!mDataBean.getOptions().isEmpty())
                {

                    holder.np_count.setValue(0);
                    holder.np_count.setActionEnabled(ActionEnum.INCREMENT,false);
                    holder.np_count.setActionEnabled(ActionEnum.DECREMENT,false);
                    mListener.onItemClick(mValues.get(position), position);
                }
            }
        });*/

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

        if(!mDataBean.isActiveWish())
        {
            holder.like_button.setChecked(false);
        }
        else {
            holder.like_button.setChecked(true);
        }

        holder.like_button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if(buttonState)
                {
                    mListener.onWishSelected(mValues.get(position),position);
                }


            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

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

        @BindView(R.id.btn_add)
        Button btn_add;

        @BindView(R.id.like_button)
        SparkButton like_button;

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

