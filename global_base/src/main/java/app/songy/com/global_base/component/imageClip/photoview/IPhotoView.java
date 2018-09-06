package app.songy.com.global_base.component.imageClip.photoview;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public interface IPhotoView {
    boolean canZoom();

    RectF getDisplayRect();

    boolean setDisplayMatrix(Matrix var1);

    Matrix getDisplayMatrix();

    /** @deprecated */
    @Deprecated
    float getMinScale();

    float getMinimumScale();

    /** @deprecated */
    @Deprecated
    float getMidScale();

    float getMediumScale();

    /** @deprecated */
    @Deprecated
    float getMaxScale();

    float getMaximumScale();

    float getScale();

    ImageView.ScaleType getScaleType();

    void setAllowParentInterceptOnEdge(boolean var1);

    /** @deprecated */
    @Deprecated
    void setMinScale(float var1);

    void setMinimumScale(float var1);

    /** @deprecated */
    @Deprecated
    void setMidScale(float var1);

    void setMediumScale(float var1);

    /** @deprecated */
    @Deprecated
    void setMaxScale(float var1);

    void setMaximumScale(float var1);

    void setOnLongClickListener(View.OnLongClickListener var1);

    void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener var1);

    void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener var1);

    void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener var1);

    void setScale(float var1);

    void setScale(float var1, boolean var2);

    void setScale(float var1, float var2, float var3, boolean var4);

    void setScaleType(ImageView.ScaleType var1);

    void setZoomable(boolean var1);

    void setPhotoViewRotation(float var1);
}
