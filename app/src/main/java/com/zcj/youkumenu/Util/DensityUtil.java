package com.zcj.youkumenu.Util;

import android.content.Context;

/**
 * Created by ZCJ on 2016/8/2.
 */
public class DensityUtil {

    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
