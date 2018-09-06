package app.songy.com.global_base.component.imageClip.photoview.log;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public interface Logger {
    int v(String var1, String var2);

    int v(String var1, String var2, Throwable var3);

    int d(String var1, String var2);

    int d(String var1, String var2, Throwable var3);

    int i(String var1, String var2);

    int i(String var1, String var2, Throwable var3);

    int w(String var1, String var2);

    int w(String var1, String var2, Throwable var3);

    int e(String var1, String var2);

    int e(String var1, String var2, Throwable var3);
}
