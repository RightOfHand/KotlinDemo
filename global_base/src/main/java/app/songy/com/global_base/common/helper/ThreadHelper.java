package app.songy.com.global_base.common.helper;

import android.os.Handler;
import android.os.Looper;

/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午10:24
 */
public final class ThreadHelper {

    public final static Handler MAIN = new Handler(Looper.getMainLooper());

    /**
     * 判断是否为主线程
     * @return boolean
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void postMain(Runnable runnable) {
        MAIN.post(runnable);
    }

    public static void postDelayed(Runnable runnable, long delayMillis) {
        MAIN.postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程上移除此Runnable
     * */
    public static void removeCallbacks(Runnable runnable) {
        MAIN.removeCallbacks(runnable);
    }

}
