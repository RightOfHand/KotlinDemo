package app.songy.com.global_base.common.helper;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * @author Created by xinzai on 2017/8/19.
 */

public class ColorHelper {

    @ColorInt
    public static int parseColor(String colorString) {
        int color;
        try {
            color = Color.parseColor(colorString);
        } catch (Exception e) {
            e.printStackTrace();
            color = Color.BLACK;
        }

        return color;
    }
}
