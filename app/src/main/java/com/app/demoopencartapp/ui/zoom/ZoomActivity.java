package com.app.demoopencartapp.ui.zoom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.utils.GlideApp;
import com.app.demoopencartapp.utils.TouchImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lenevo on 14-09-2017.
 */

public class ZoomActivity extends BaseActivity implements ZoomMvpView {

    private String zoomImage;
/*    private Matrix matrix = new Matrix();
    private float scale = 1f;*/


    @BindView(R.id.product_image)
    TouchImageView product_image;

    @Inject
    ZoomPresenter<ZoomMvpView> zoomPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zoom);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    private void setUp()
    {
        zoomPresenter.onAttach(this);

        Bundle bundle = getIntent().getExtras();
        zoomImage = bundle.getString("ZoomImage","");
        if(!zoomImage.equals("")) {
            zoomPresenter.onShowImage(zoomImage);
        }

    }

    @Override
    protected void onDestroy() {
        zoomPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showImage(String product_img) {
        GlideApp.with(getApplicationContext())
                .load(zoomImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()

                .into(product_image);
        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float x = product_image.getScaleX();
                float y = product_image.getScaleY();

                product_image.setScaleX((float) (x+1));
                product_image.setScaleY((float) (y+1));
            }
        });
    }

/*    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));
            matrix.setScale(scale, scale);
            product_image.setImageMatrix(matrix);
            return true;
        }
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }




}
