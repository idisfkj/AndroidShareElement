package com.idisfkj.androidshareelement;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.idisfkj.sharelibrary.FKJShareElement;
import com.idisfkj.sharelibrary.ShareElementInfo;

public class ShareElementActivity extends AppCompatActivity {

    private final long ANIMATOR_DURATION = 1000;

    private ImageView mImageView;
    private FKJShareElement mShareElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element);
        mImageView = (ImageView) findViewById(R.id.image_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView.setTransitionName(MainActivity.TRANSITION_NAME_SHARE);
        } else {
            ShareElementInfo info = getIntent().getExtras().getParcelable(MainActivity.EXTRA_SHARE_ELEMENT_INFO);
            mShareElement = new FKJShareElement(info, this, mImageView.getRootView());
            mShareElement.convert(mImageView)
                    .setDuration(ANIMATOR_DURATION)
                    .setInterpolator(new LinearInterpolator())
                    .startEnterAnimator();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            mShareElement.convert(mImageView)
                    .setDuration(ANIMATOR_DURATION)
                    .setInterpolator(new LinearInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            finish();
                            overridePendingTransition(0, 0);
                        }
                    })
                    .startExitAnimator();
        }
    }
}
