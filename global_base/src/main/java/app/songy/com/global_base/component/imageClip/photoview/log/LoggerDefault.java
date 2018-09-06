package app.songy.com.global_base.component.imageClip.photoview.log;

import android.util.Log;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public class LoggerDefault implements Logger {
    public LoggerDefault() {
    }

    public int v(String tag, String msg) {
        return Log.v(tag, msg);
    }

    public int v(String tag, String msg, Throwable tr) {
        return Log.v(tag, msg, tr);
    }

    public int d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    public int d(String tag, String msg, Throwable tr) {
        return Log.d(tag, msg, tr);
    }

    public int i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    public int i(String tag, String msg, Throwable tr) {
        return Log.i(tag, msg, tr);
    }

    public int w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    public int w(String tag, String msg, Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    public int e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    public int e(String tag, String msg, Throwable tr) {
        return Log.e(tag, msg, tr);
    }
}
