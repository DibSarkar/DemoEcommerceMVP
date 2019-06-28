package com.app.demoopencartapp.ui.reviews;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.data.network.models.ReviewsResponse;
import com.app.demoopencartapp.ui.productDetails.SimilarProductsAdapter;
import com.app.demoopencartapp.utils.GlideApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>   {

    private final ArrayList<ReviewsResponse.ReviewListBean> mValues;


    public ReviewListListener mListener;
    Context mContext;

    public ReviewAdapter(ArrayList<ReviewsResponse.ReviewListBean> items) {
        mValues = items;

    }

    public interface ReviewListListener {
        void onItemClick(ReviewsResponse.ReviewListBean item, int position);
    }

    public void setAdapterListener(ReviewListListener mListener) {
        this.mListener = mListener;
    }


    public void loadReviews(List<ReviewsResponse.ReviewListBean> reviewListBeanList) {

        mValues.addAll(reviewListBeanList);
        notifyDataSetChanged();
    }


    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_review, parent, false);
        mContext=parent.getContext();
        return new ReviewAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ReviewAdapter.ViewHolder holder, final int position) {
        final ReviewsResponse.ReviewListBean mDataBean = mValues.get(position);
        holder.tv_reviewer.setText(mDataBean.getName());
        holder.tv_review_date.setText(mDataBean.getDate_added());
        holder.tv_review.setText(mDataBean.getReview());
        holder.rb_product.setRating((float)mDataBean.getRating());

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

        @BindView(R.id.tv_reviewer)
        TextView tv_reviewer;

        @BindView(R.id.tv_review_date)
        TextView tv_review_date;

        @BindView(R.id.tv_review)
        TextView tv_review;

        @BindView(R.id.rb_product)
        RatingBar rb_product;


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

