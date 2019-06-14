package com.app.demoopencartapp.ui;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.app.demoopencartapp.R;



/**
 * Created by lenevo on 14-05-2018.
 */

public class DoubleDrawerView extends ViewFlipper {
    private static final int NONE = -1;
    private static final int MAIN_VIEW_INDEX = 0;
    private static final int DRAWER_VIEW_INDEX = 1;
    private static final int DRAWER_NESTED_INDEX = 2;

    private Animation slideInAnimation, slideOutAnimation, noAnimation;
    private boolean animating = false;

    private Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation anim) {
            animating = false;
        }

        @Override
        public void onAnimationStart(Animation anim) {}

        @Override
        public void onAnimationRepeat(Animation anim) {}
    };

    public DoubleDrawerView(Context context) {
        this(context, null);
    }

    public DoubleDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        slideInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        slideOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        noAnimation = AnimationUtils.loadAnimation(context, R.anim.none);
        noAnimation.setAnimationListener(listener);
    }

    public void openInnerDrawer() {
        if (getDisplayedChild() != DRAWER_VIEW_INDEX) {
            setChildAndAnimate(DRAWER_VIEW_INDEX, true);
        }
    }
    public void openNestedDrawer() {
        if (getDisplayedChild() != DRAWER_NESTED_INDEX) {
            setChildAndAnimate(DRAWER_NESTED_INDEX, true);
        }
    }

    public void closeInnerDrawer() {
        if (getDisplayedChild() != MAIN_VIEW_INDEX) {
            setChildAndAnimate(MAIN_VIEW_INDEX, true);
        }
    }
    public void closeNestedDrawer() {
        if (getDisplayedChild() != DRAWER_VIEW_INDEX) {
            setChildAndAnimate(DRAWER_VIEW_INDEX, true);
        }
    }

    public boolean isInnerDrawerOpen() {
        return getDisplayedChild() == DRAWER_VIEW_INDEX;
    }

    private void setChildAndAnimate(int whichChild, boolean doAnimate) {
        if (doAnimate) {
            setAnimationForChild(whichChild);
        }
        else {
            setAnimationForChild(NONE);
        }
        animating = doAnimate;
        setDisplayedChild(whichChild);
    }

    private void setAnimationForChild(int whichChild) {
        if (whichChild == DRAWER_VIEW_INDEX) {
            setInAnimation(slideInAnimation);
            setOutAnimation(noAnimation);
        }

        else if(whichChild == DRAWER_NESTED_INDEX)
        {
            setInAnimation(slideInAnimation);
            setOutAnimation(noAnimation);
        }
        else if (whichChild == MAIN_VIEW_INDEX) {
            setInAnimation(noAnimation);
            setOutAnimation(slideOutAnimation);
        }
        else {
            setInAnimation(null);
            setOutAnimation(null);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (animating) {
            return true;
        }
        else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.whichChild = getDisplayedChild();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setChildAndAnimate(ss.whichChild, false);
    }

    private static class SavedState extends BaseSavedState {
        int whichChild;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            whichChild = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(whichChild);
        }

        public static final Parcelable.Creator<SavedState>
                CREATOR = new Parcelable.Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

