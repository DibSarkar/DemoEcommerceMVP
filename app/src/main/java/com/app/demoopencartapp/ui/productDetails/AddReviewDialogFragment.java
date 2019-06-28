package com.app.demoopencartapp.ui.productDetails;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.di.components.ActivityComponent;
import com.app.demoopencartapp.shared.base.BaseDialog;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReviewDialogFragment extends BaseDialog implements AddRateDialogMvpView {

    public static final String TAG = AddReviewDialogFragment.class.getSimpleName();

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_review)
    EditText et_review;

    @BindView(R.id.rb_review)
    RatingBar rb_review;

    String product_id;



    @Inject
    AddDialogPresenter<AddRateDialogMvpView> addDialogPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().getString("product_id")!= null){

            product_id=getArguments().getString("product_id");
        }


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dialog_rating, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, v));

            addDialogPresenter.onAttach(this);
        }
        setUp(v);
        return v;
    }

    @Override
    protected void setUp(View view) {
        setUnBinder(ButterKnife.bind(this,view));

    }


    @Override
    public void onDestroyView() {
        addDialogPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void dismissDialog(String tag) {
        super.dismissDialog(tag);
    }

    @OnClick({R.id.btn_submit,R.id.iv_cross})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_submit:
                addDialogPresenter.onConfirmSendRating(et_name.getText().toString(),et_review.getText().toString(),rb_review.getRating(),product_id);

                break;

            case R.id.iv_cross :
                addDialogPresenter.onCloseDialog();
                break;
        }

    }

    @Override
    public void showAlert(String message) {

    }

    @Override
    public void showInactiveUserAlert(String message) {

    }

    @Override
    public void logOut() {

    }

    @Override
    public void sendRating(String name, String review, float rating, String product_id) {
        addDialogPresenter.onSendRating(name,review,rating,product_id);

    }

    @Override
    public void closeDialog() {
        dismissDialog("");

    }

    @Override
    public void ratingDone(int rating, String reviews) {
        dismissDialog("");

        ((ProductDetailsActivity) getActivity()).setRatingText(String.valueOf(rating),reviews);
    }
}
