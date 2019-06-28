package com.app.demoopencartapp.ui.reviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.ReviewsResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.utils.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsListActivity extends BaseActivity implements ReviewsListMvpView{

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_reviews)
    RecyclerView rv_reviews;

    @Inject
    ReviewsPresenter<ReviewsListMvpView> reviewsPresenter;

    @Inject
    ReviewAdapter reviewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reviews);
        mContext = this;
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }


    private void setUp() {
        reviewsPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        reviewsPresenter.onGetReviews(getIntent().getExtras().getString("product_id"));


    }

    @Override
    protected void onDestroy() {
        reviewsPresenter.onDetach();
        super.onDestroy();
    }

    private void loadAdapter()
    {
        rv_reviews.setHasFixedSize(true);
        rv_reviews.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_reviews.setItemAnimator(new DefaultItemAnimator());
        rv_reviews.setNestedScrollingEnabled(false);
        rv_reviews.setAdapter(reviewAdapter);
        Drawable dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.divider);
        rv_reviews.addItemDecoration(new DividerItemDecoration(dividerDrawable,false,false));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void getReviews(List<ReviewsResponse.ReviewListBean> reviewList) {

        if(reviewList.size()>0)
        {
            loadAdapter();
            reviewAdapter.loadReviews(reviewList);
        }

    }
}
