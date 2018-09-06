package app.songy.com.global_base.component.imageClip.photoview.gestures;

import android.view.MotionEvent;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public interface GestureDetector {
    boolean onTouchEvent(MotionEvent var1);

    boolean isScaling();

    void setOnGestureListener(OnGestureListener var1);
}
