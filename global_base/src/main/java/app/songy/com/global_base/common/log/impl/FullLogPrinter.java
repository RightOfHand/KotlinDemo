package app.songy.com.global_base.common.log.impl;


import app.songy.com.global_base.common.log.ILogPrinter;

/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午9:52
 */
public class FullLogPrinter implements ILogPrinter {
//    static {
//        Logger.init().setMethodCount(2).setMethodOffset(5);
//    }

    @Override
    public void printI(String tag, String content, Object... args) {
        //Logger.t(tag).i(content, args);
    }

    @Override
    public void printE(String tag, String content, Object... args) {
        //Logger.t(tag).e(content, args);
    }

    @Override
    public void printD(String tag, String content, Object... args) {
        //Logger.t(tag).d(content, args);
    }

    @Override
    public void printW(String tag, String content, Object... args) {
        //Logger.t(tag).w(content, args);
    }

    @Override
    public void printE(String tag, Throwable throwable, String content, Object... args) {
        //Logger.t(tag).e(throwable, content, args);
    }
}
