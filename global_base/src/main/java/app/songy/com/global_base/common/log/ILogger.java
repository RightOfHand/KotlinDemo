package app.songy.com.global_base.common.log;


import app.songy.com.global_base.common.listener.LogTag;

/**
 /**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午9:52
 */
public interface ILogger {

    void d(String content, Object... args);

    void d(LogTag tag, String content, Object... args);

    void e(String content, Object... args);

    void e(LogTag tag, String content, Object... args);

    void e(Throwable throwable);

    void e(LogTag tag, Throwable throwable);

    void e(LogTag tag, Throwable throwable, String content, Object... args);

    void e(Throwable throwable, String content, Object... args);

    void i(LogTag tag, String content, Object... args);

    void i(String content, Object... args);

    void w(LogTag tag, String content, Object... args);

    void w(String content, Object... args);

}
