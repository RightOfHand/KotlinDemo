package app.songy.com.global_base.common.log;


public interface ILogPrinter {

    void printI(String tag, String content, Object... args);

    void printE(String tag, String content, Object... args);

    void printD(String tag, String content, Object... args);

    void printW(String tag, String content, Object... args);

    void printE(String tag, Throwable throwable, String content, Object... args);
}
