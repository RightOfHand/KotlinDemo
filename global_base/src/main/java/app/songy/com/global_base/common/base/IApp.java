package app.songy.com.global_base.common.base;

import android.content.Context;

/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午10:55
 */

public interface IApp {
    Context getContext();

    long getStartTime();

    boolean isLogD();

    boolean isLogE();

    boolean isBeta();

    boolean isRelease();

    boolean isDebug();
}
