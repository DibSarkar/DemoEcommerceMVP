package com.app.demoopencartapp.ui.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.CartListBean;
import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.utils.GlideApp;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>   {

    private final ArrayList<CartListResponse.CartlistBean.ProductsBean> mValues;


    public CartListener mListener;
    Context mContext;

    public CartAdapter(ArrayList<CartListResponse.CartlistBean.ProductsBean> items) {
        mValues = items;

    }

    public interface CartListener {
        void onItemClick(CartListResponse.CartlistBean.ProductsBean item, int position);
        void onUpdate(CartListResponse.CartlistBean.ProductsBean item, int position, int old_value);
        void onDelete(CartListResponse.CartlistBean.ProductsBean item, int position);
    }

    public void setAdapterListener(CartListener mListener) {
        this.mListener = mListener;
    }


    public void loadProducts(List<CartListResponse.CartlistBean.ProductsBean> cartListBeanList) {

        if(mValues!=null) {
            if (mValues.size() > 0) {
                mValues.clear();
            }
        }
        mValues.addAll(cartListBeanList);
        notifyDataSetChanged();
    }


    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_cart, parent, false);
        mContext = parent.getContext();
        return new CartAdapter.ViewHolder(view);
    }

    public void changeCartQuantity(CartListResponse.CartlistBean.ProductsBean productsBean,int old_value)
    {
        for (int i = 0; i <mValues.size() ; i++) {

            if(mValues.get(i).getCart_id().equals(productsBean.getCart_id()))
            {
                mValues.get(i).setCart_item(String.valueOf(old_value));
                notifyDataSetChanged();
                return;
            }
        }

    }


    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position) {

        final CartListResponse.CartlistBean.ProductsBean mDataBean = mValues.get(position);

        holder.tv_name.setText(mDataBean.getName());
        holder.tv_model.setText(mDataBean.getModel());
        holder.tv_price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(mDataBean.getPrice()))));
        if(mDataBean.getTax()!=0) {
            holder.tv_tax.setVisibility(View.VISIBLE);
            holder.tv_tax.setText("excluding " + mDataBean.getTax() + "% GST");
        }
        else {
            holder.tv_tax.setVisibility(View.GONE);
        }

        GlideApp.with(mContext)
                .load(mDataBean.getImage())
                .placeholder(R.drawable.no_image)
                .centerCrop()
                .into(holder.iv_pro);

        holder.np_count.setValue(Integer.parseInt(mDataBean.getCart_item()));

        holder.np_count.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {

                mListener.onUpdate(mValues.get(position),position,value);

            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDelete(mValues.get(position),position);
            }
        });

        holder.iv_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(mValues.get(position),position);
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

        @BindView(R.id.iv_pro)
        ImageView iv_pro;

        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_model)
        TextView tv_model;

        @BindView(R.id.tv_price)
        TextView tv_price;

        @BindView(R.id.tv_tax)
        TextView tv_tax;

        @BindView(R.id.np_count)
        NumberPicker np_count;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }
}

