package app.songy.com.global_base.common.log.impl;

import android.util.Log;

import app.songy.com.global_base.common.log.ILogPrinter;


/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午9:52
 */
public class SimpleLogPrinter implements ILogPrinter {
    protected void info(String tag, String content) {
    }

    @Override
    public void printI(String tag, String content, Object... args) {
        String realContent = getContent(content, args);
        Log.i(tag, realContent);
        info(tag, realContent);
    }

    @Override
    public void printE(String tag, String content, Object... args) {
        String realContent = getContent(content, args);
        Log.e(tag, realContent);
        info(tag, realContent);
    }

    @Override
    public void printD(String tag, String content, Object... args) {
        String realContent = getContent(content, args);
        Log.d(tag, realContent);
        info(tag, realContent);
    }

    @Override
    public void printW(String tag, String content, Object... args) {
        String realContent = getContent(content, args);
        Log.w(tag, realContent);
        info(tag, realContent);
    }

    @Override
    public void printE(String tag, Throwable throwable, String content, Object... args) {
        String realContent = getContent(content, args);
        Log.e(tag, realContent, throwable);
        info(tag, realContent);
    }

    private String getContent(String msg, Object... args) {
        try {
            String pre = getNameFromTrace(Thread.currentThread().getStackTrace(), 5);
            return pre + String.format(msg, args);
        } catch (Throwable throwable) {
            return msg;
        }
    }
    protected final String getNameFromTrace(StackTraceElement[] traceElements, int place) {
        StringBuffer taskName = new StringBuffer();
        if (traceElements != null && traceElements.length > place) {
            StackTraceElement traceElement = traceElements[place];
            taskName.append(traceElement.getMethodName());
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")");
        }
        return taskName.toString();
    }

}
