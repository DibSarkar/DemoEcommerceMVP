package com.app.demoopencartapp.ui.productDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VariationsAdapter extends RecyclerView.Adapter<VariationsAdapter.ViewHolder>   {

    private final List<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> mValues;


    public VariationListener mListener;
    Context mContext;

    public VariationsAdapter(ArrayList<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> items) {
        mValues = items;

    }

    public interface VariationListener {
        void onItemClick(ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean item, int position);
    }

    public void setAdapterListener(VariationListener mListener) {
        this.mListener = mListener;
    }


    public void loadVariations(List<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> productOptionValueBeanList) {

        mValues.addAll(productOptionValueBeanList);
        notifyDataSetChanged();
    }
    public void wishSelect(String product_option_value_id, boolean isCheckWish, int wish_id)
    {

        for (int i=0;i<mValues.size();i++)
        {
                 if (mValues.get(i).getProduct_option_value_id().equals(product_option_value_id)) {
                     mValues.get(i).setWishlist_id(String.valueOf(wish_id));
                     if(isCheckWish) {
                         mValues.get(i).setIs_wishlist("1");
                     }
                     else {
                         mValues.get(i).setIs_wishlist("0");
                     }
             }


        }
        notifyDataSetChanged();
    }


    public void changeItemBg(ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean productOptionValueBean,int pos)
    {

        for (int i = 0; i < mValues.size(); i++) {
            if(mValues.get(pos).getProduct_option_value_id().equals(mValues.get(i).getProduct_option_value_id()))
            {
                mValues.get(i).setSelected(true);
            }
            else {
                mValues.get(i).setSelected(false);
            }
        }

        notifyDataSetChanged();
    }

    public void changeItemBgWish(ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean productOptionValueBean,boolean selectWish,int pos)
    {


            for (int j=0;j<mValues.size();j++) {
                if (mValues.get(pos).getProduct_option_value_id().equals(productOptionValueBean.getProduct_option_value_id())) {
                   if(mValues.get(j).getIs_wishlist().equals("1")) {
                       if (selectWish) {
                           mValues.get(j).setIs_wishlist("1");
                       }
                   }
                   else {

                   }
                }





        }

        notifyDataSetChanged();
    }
    @Override
    public VariationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_variations, parent, false);
        mContext=parent.getContext();
        return new VariationsAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final VariationsAdapter.ViewHolder holder, final int position) {

        final ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean mDataBean = mValues.get(position);
        holder.tv_size.setText(mDataBean.getName());
        if(mDataBean.isSelected())
        {
            holder.ll_size.setBackgroundResource(R.drawable.variations_active_border);
        }
        else {
            holder.ll_size.setBackgroundResource(R.drawable.variations_border);
        }



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

        @BindView(R.id.tv_size)
        TextView tv_size;

        @BindView(R.id.ll_size)
        LinearLayout ll_size;



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






