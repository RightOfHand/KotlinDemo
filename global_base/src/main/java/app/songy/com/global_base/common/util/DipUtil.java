package app.songy.com.global_base.common.util;

import android.content.Context;

/**
 * Description:
 * Created by song on 2018/6/11.
 * email：bjay20080613@qq.com
 */

public class DipUtil {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
