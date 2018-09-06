package app.songy.com.global_base.component.imageClip.photoview.gestures;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public interface OnGestureListener {
    void onDrag(float var1, float var2);

    void onFling(float var1, float var2, float var3, float var4);

    void onScale(float var1, float var2, float var3);
}
