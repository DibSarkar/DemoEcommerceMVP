package com.app.demoopencartapp.ui.addAddress;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.demoopencartapp.R;

import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;

import java.util.ArrayList;
import java.util.List;

public class CountryStateAdpater extends BaseAdapter {


    ArrayList<CountriesStatesResponse.DataBean> mList;
   Context context;

    int pos;

    public CountryStateAdpater(ArrayList<CountriesStatesResponse.DataBean> dataBeanArrayList) {


        mList = dataBeanArrayList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static final class ViewHolder {
        TextView text1;
    }

    public void loadAllCountries(List<CountriesStatesResponse.DataBean> dataBeanList)
    {
        mList.clear();
        mList.addAll(dataBeanList);
        notifyDataSetChanged();
    }

    public void loadStates(List<CountriesStatesResponse.DataBean> dataBeanList)
    {
        mList.clear();
        mList.addAll(dataBeanList);
        notifyDataSetChanged();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        return initView(position, convertView);
    }

    private View initView(int position, View convertView) {
        if(convertView == null)
            convertView = View.inflate(context,
                    android.R.layout.simple_list_item_1,
                    null);
        TextView tvText1 = convertView.findViewById(android.R.id.text1);
        tvText1.setText(mList.get(position).getName());
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        pos = position;

        if(convertView==null){
            convertView = View.inflate(parent.getContext(),
                    R.layout.layout_item_state_country,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.tv_state_country);
            convertView.setTag(viewHolder);
        }else{
            // just re-use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text1.setText(mList.get(position).getName());

        return convertView;
    }
}

