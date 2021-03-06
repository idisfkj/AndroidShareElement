package com.idisfkj.sharelibrary;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.ImageView;

/**
 * Created by idisfkj on 17/7/10.
 * Email : idisfkj@gmail.com.
 */

public class FKJShareElement {

    private final long ANIMATOR_DURATION = 300;

    private long mDuration = ANIMATOR_DURATION;
    private ShareElementInfo mInfo;
    private AnimatorListenerAdapter mListener;
    private ViewPropertyAnimator mAnimator;
    private Interpolator mInterpolator;
    private boolean mEnter;
    private Context mContext;
    private View mBgView;

    public FKJShareElement(ShareElementInfo info, Context context, View bgView) {
        mInfo = info;
        mContext = context;
        mBgView = bgView;
    }

    /**
     * convert target ImageView info and if enter animation to init
     *
     * @param tarView the second page share view
     * @return Class self
     */
    public FKJShareElement convert(final ImageView tarView) {
        if (mInfo == null) {
            throw new NullPointerException("ShareElementInfo must not null");
        }
        tarView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tarView.getViewTreeObserver().removeOnPreDrawListener(this);
                mInfo.convertTargetInfo(tarView, mContext);
                //init
                if (mEnter) {
                    tarView.setPivotX(mInfo.getPivotX());
                    tarView.setPivotY(mInfo.getPivotY());
                    tarView.setTranslationX(mInfo.getCenterOffsetX());
                    tarView.setTranslationY(mInfo.getCenterOffsetY());
                    tarView.setScaleX(mInfo.getScaleX());
                    tarView.setScaleY(mInfo.getScaleY());
                    mAnimator = tarView.animate();
                    start();
                    startBackgroundAlphaAnimation(mBgView, new ColorDrawable(ContextCompat.getColor(mContext, R.color.fkj_white)));
                }
                return true;
            }
        });
        return this;
    }

    public FKJShareElement setDuration(long duration) {
        mDuration = duration;
        return this;
    }

    public FKJShareElement setListener(AnimatorListenerAdapter listener) {
        mListener = listener;
        return this;
    }

    public FKJShareElement setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        return this;
    }

    private void start() {
        mAnimator.setDuration(mDuration)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .translationX(0)
                .translationY(0);
        if (mListener != null) {
            mAnimator.setListener(mListener);
        }
        if (mInterpolator != null) {
            mAnimator.setInterpolator(mInterpolator);
        }
        mAnimator.start();
    }

    public void startEnterAnimator() {
        mEnter = true;
    }

    public void startExitAnimator() {
        mEnter = false;
        mAnimator.setDuration(mDuration)
                .scaleX(mInfo.getScaleX())
                .scaleY(mInfo.getScaleY())
                .translationX(mInfo.getCenterOffsetX())
                .translationY(mInfo.getCenterOffsetY());
        if (mListener != null) {
            mAnimator.setListener(mListener);
        }
        if (mInterpolator != null) {
            mAnimator.setInterpolator(mInterpolator);
        }
        mAnimator.start();
        startBackgroundAlphaAnimation(mBgView, new ColorDrawable(ContextCompat.getColor(mContext, R.color.fkj_white)), 255, 0);
    }

    private void startBackgroundAlphaAnimation(final View bgView, final ColorDrawable colorDrawable, int... value) {
        if (bgView == null)
            return;
        if (value == null || value.length == 0) {
            value = new int[]{0, 255};
        }
        ObjectAnimator animator = ObjectAnimator.ofInt(colorDrawable, "alpha", value);
        animator.setDuration(mDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bgView.setBackground(colorDrawable);
            }
        });
        animator.start();
    }
}
