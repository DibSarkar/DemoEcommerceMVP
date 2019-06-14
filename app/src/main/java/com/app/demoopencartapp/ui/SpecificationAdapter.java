package com.app.demoopencartapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.SpecsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.ViewHolder>   {

    private final ArrayList<SpecsBean> mValues;


    public SpecsListener mListener;
    Context mContext;

    public SpecificationAdapter(ArrayList<SpecsBean> items) {
        mValues = items;

    }

    public interface SpecsListener {
        void onItemClick(SpecsBean item, int position);
    }

    public void setAdapterListener(SpecsListener mListener) {
        this.mListener = mListener;
    }


    public void loadSpecs(List<SpecsBean> specsBeanList) {

        mValues.addAll(specsBeanList);
        notifyDataSetChanged();
    }


    @Override
    public SpecificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_specification, parent, false);
        mContext=parent.getContext();
        return new SpecificationAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final SpecificationAdapter.ViewHolder holder, final int position) {
        final SpecsBean mDataBean = mValues.get(position);
        holder.tv_spec_key.setText(mDataBean.getSpec_key());
        holder.tv_spec_value.setText(mDataBean.getSpec_value());
        if(position==mValues.size()-1)
        {
            holder.view_spec.setVisibility(View.GONE);
        }
        else {
            holder.view_spec.setVisibility(View.VISIBLE);
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

        @BindView(R.id.tv_spec_key)
        TextView tv_spec_key;

        @BindView(R.id.tv_spec_value)
        TextView tv_spec_value;

        @BindView(R.id.view_spec)
        View view_spec;



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



