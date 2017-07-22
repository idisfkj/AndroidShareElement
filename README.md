# AndroidShareElement

Compatible with Android share animation

# Getting Started

in your `build.gradle`:

```
dependencies {
   compile 'com.idisfkj.share:sharelibrary:1.0.0'
}
```

# Use Step
You just need to follow the following three steps to use.

## One Step
In the first activity, create a ShareElementInfo Object and to convert useful information from need share view. For example the code below:

```
ShareElementInfo info = new ShareElementInfo();
info.convertOriginalInfo(imageView);
intent.putExtra(EXTRA_SHARE_ELEMENT_INFO, info);
startActivity(intent);
overridePendingTransition(0, 0);
```

## Second Step
Then in the second activity which should jump activity, To start share Animation.

```
ShareElementInfo info = getIntent().getExtras().getParcelable(MainActivity.EXTRA_SHARE_ELEMENT_INFO);
mShareElement = new FKJShareElement(info, this, mImageView.getRootView());
mShareElement.convert(mImageView)
           .setDuration(ANIMATOR_DURATION)
           .setInterpolator(new LinearInterpolator())
           .startEnterAnimator();
```

`note`：you should set the `android:them` to transparent int the activity. For example:

```
<item name="android:windowBackground">@android:color/transparent</item>
```

## Third Step

Finally, I recommend to overriding `onBackPressed` method in order to efficient exit the activity.

```
@Override
public void onBackPressed() {
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
```

# More Link

[Android共享动画兼容实现](https://idisfkj.github.io/2017/07/16/Android%E5%85%B1%E4%BA%AB%E5%8A%A8%E7%94%BB%E5%85%BC%E5%AE%B9%E5%AE%9E%E7%8E%B0/)

# License

```
Copyright 2017 idisfkj, personal.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```