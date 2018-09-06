package app.songy.com.global_base.component.imageClip.photoview.gestures;

import android.content.Context;
import android.os.Build;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public class VersionedGestureDetector {
    public VersionedGestureDetector() {
    }

    public static GestureDetector newInstance(Context context, OnGestureListener listener) {
        int sdkVersion = Build.VERSION.SDK_INT;
        Object detector;
        if(sdkVersion < 5) {
            detector = new CupcakeGestureDetector(context);
        } else if(sdkVersion < 8) {
            detector = new EclairGestureDetector(context);
        } else {
            detector = new FroyoGestureDetector(context);
        }

        ((GestureDetector)detector).setOnGestureListener(listener);
        return (GestureDetector)detector;
    }
}
