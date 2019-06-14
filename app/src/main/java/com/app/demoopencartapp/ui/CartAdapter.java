package com.app.demoopencartapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.CartListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>   {

    private final ArrayList<CartListBean> mValues;


    public CartListener mListener;
    Context mContext;

    public CartAdapter(ArrayList<CartListBean> items) {
        mValues = items;

    }

    public interface CartListener {
        void onItemClick(CartListBean item, int position);
    }

    public void setAdapterListener(CartListener mListener) {
        this.mListener = mListener;
    }


    public void loadProducts(List<CartListBean> cartListBeanList) {

        mValues.addAll(cartListBeanList);
        notifyDataSetChanged();
    }


    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position) {

        final CartListBean mDataBean = mValues.get(position);


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



        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }
}

