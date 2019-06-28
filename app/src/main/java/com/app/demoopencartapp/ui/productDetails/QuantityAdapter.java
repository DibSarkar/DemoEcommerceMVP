package com.app.demoopencartapp.ui.productDetails;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.demoopencartapp.R;

import java.util.ArrayList;

public class QuantityAdapter extends BaseAdapter {


    ArrayList<Integer> mList;

    Context mContext;
    int pos;

    public QuantityAdapter(ArrayList<Integer> gList) {


        this.mList = gList;
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static final class ViewHolder {
        TextView text1;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        mContext = parent.getContext();
        return initView(position, convertView);
    }

    private View initView(int position, View convertView) {

        if (convertView == null)
            convertView = View.inflate(mContext,
                    android.R.layout.simple_list_item_1,
                    null);
        TextView tvText1 = convertView.findViewById(android.R.id.text1);
        tvText1.setText(String.valueOf(mList.get(position)));
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        pos = position;

        if (convertView == null) {
            mContext = parent.getContext();
            convertView = View.inflate(mContext,
                    R.layout.layout_item_quantity,
                    null);

            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.tv_quantity);
            convertView.setTag(viewHolder);
        } else {
            // just re-use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text1.setText(String.valueOf(mList.get(position)));

        return convertView;
    }



    public void loadQuantities(ArrayList<Integer> quantityList) {

        mList.clear();
        mList.addAll(quantityList);

        notifyDataSetChanged();
    }

}