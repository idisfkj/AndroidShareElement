package com.idisfkj.sharelibrary;

import android.content.Context;

/**
 * Created by idisfkj on 17/7/10.
 * Email : idisfkj@gmail.com.
 */

public class CommonUtils {
    /**
     * get statusbar height
     *
     * @param context
     * @return statusbar height
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
