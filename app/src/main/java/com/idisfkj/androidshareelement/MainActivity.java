package com.idisfkj.androidshareelement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.idisfkj.sharelibrary.ShareElementInfo;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_SHARE_ELEMENT_INFO = "share_element_info";
    public static final String TRANSITION_NAME_SHARE = "share";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareElementActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageView.setTransitionName(TRANSITION_NAME_SHARE);
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imageView, TRANSITION_NAME_SHARE);
                    startActivity(intent, compat.toBundle());
                } else {
                    ShareElementInfo info = new ShareElementInfo();
                    info.convertOriginalInfo(imageView);
                    intent.putExtra(EXTRA_SHARE_ELEMENT_INFO, info);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });
    }
}
