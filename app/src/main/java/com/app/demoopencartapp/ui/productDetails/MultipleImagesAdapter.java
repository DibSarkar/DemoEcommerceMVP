package com.app.demoopencartapp.ui.productDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.ui.MultipleAdapter;
import com.app.demoopencartapp.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenevo on 28-06-2018.
 */

public class MultipleImagesAdapter extends RecyclerView.Adapter<MultipleImagesAdapter.ViewHolder>   {

    private final List<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean> mValues;


    public MultipleImagesListener mListener;
    Context mContext;

    public MultipleImagesAdapter(ArrayList<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean> items) {
        mValues = items;

    }

    public interface MultipleImagesListener {
        void onItemClick(ProductDetailsResponse.ProductBean.ImagesBean.ImageBean item, int position);
    }

    public void setAdapterListener(MultipleImagesListener mListener) {
        this.mListener = mListener;
    }


    public void loadImages(List<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean> multipleList) {

        mValues.addAll(multipleList);
        notifyDataSetChanged();
    }


    @Override
    public MultipleImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_img, parent, false);
        mContext=parent.getContext();
        return new MultipleImagesAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final MultipleImagesAdapter.ViewHolder holder, final int position) {

        final ProductDetailsResponse.ProductBean.ImagesBean.ImageBean mDataBean = mValues.get(position);
        GlideApp.with(mContext)
                .load(mDataBean.getPopup())
                .placeholder(R.drawable.no_image)
                .centerCrop()
                .into(holder.iv_mul_image);

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

        @BindView(R.id.iv_mul_image)
        ImageView iv_mul_image;



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





