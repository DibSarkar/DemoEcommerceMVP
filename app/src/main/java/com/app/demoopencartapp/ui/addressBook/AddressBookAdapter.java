package com.app.demoopencartapp.ui.addressBook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demoopencartapp.R;

import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.ui.HomeOffersListAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.ViewHolder>   {

    private final ArrayList<AddressListResponse.ResponseDataBean> mValues;


    public AddressBookListener mListener;
    public AddressBookDeleteListener mListener1;
    Context mContext;

    public AddressBookAdapter(ArrayList<AddressListResponse.ResponseDataBean> items) {
        this.mValues = items;

    }

    public interface AddressBookListener {
        void onItemClick(AddressListResponse.ResponseDataBean item, int position);
        void onItemDelete(AddressListResponse.ResponseDataBean item, int position);
        void onItemEdit(AddressListResponse.ResponseDataBean item);
    }

    public interface AddressBookDeleteListener
    {
        void onItemCheckAddress(List<AddressListResponse.ResponseDataBean> responseDataBeanList);
    }

    public void setAdapterListener(AddressBookListener mListener) {
        this.mListener = mListener;
    }
    public void setAdapterListener1(AddressBookDeleteListener mListener) {
        this.mListener1 = mListener;
    }




    public void loadAddress(List<AddressListResponse.ResponseDataBean> responseDataBeanList) {
        mValues.clear();
        mValues.addAll(responseDataBeanList);
        notifyDataSetChanged();


    }

    public void deleteAddress(AddressListResponse.ResponseDataBean responseDataBean)
    {

        mValues.remove(responseDataBean);
        mListener1.onItemCheckAddress(mValues);
        notifyDataSetChanged();


    }


    @Override
    public AddressBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_address, parent, false);
        mContext=parent.getContext();
        return new AddressBookAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final AddressListResponse.ResponseDataBean mDataBean = mValues.get(position);
        holder.tv_address_name.setText(mDataBean.getFirstname()+" "+mDataBean.getLastname()+", "+mDataBean.getAddress_1()+" "+mDataBean.getAddress_2()+", "+mDataBean.getCity()+", "+mDataBean.getPostcode()+", "+mDataBean.getZone()+" "+mDataBean.getCountry());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemDelete(mValues.get(position),position);
            }
        });
        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemEdit(mValues.get(position));
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

        @BindView(R.id.tv_address_name)
        TextView tv_address_name;

        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        @BindView(R.id.iv_edit)
        ImageView iv_edit;


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



