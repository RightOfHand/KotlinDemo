package app.songy.com.global_base.component.imageClip.photoview.scrollerproxy;

import android.content.Context;
import android.widget.Scroller;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public class PreGingerScroller extends ScrollerProxy{
    private Scroller mScroller;

    public PreGingerScroller(Context context) {
        this.mScroller = new Scroller(context);
    }

    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    }

    public void forceFinished(boolean finished) {
        this.mScroller.forceFinished(finished);
    }

    public boolean isFinished() {
        return this.mScroller.isFinished();
    }

    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    public int getCurrY() {
        return this.mScroller.getCurrY();
    }
}
