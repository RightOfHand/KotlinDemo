package app.songy.com.global_base.common.helper;

import app.songy.com.global_base.common.listener.LogTag;
import app.songy.com.global_base.common.log.ILogControl;
import app.songy.com.global_base.common.log.ILogPrinter;
import app.songy.com.global_base.common.log.LogScheduler;
import app.songy.com.global_base.common.log.impl.SimpleLogPrinter;

/**
 * Description:
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

public class LogHelper {
    private static ILogControl defaultILogControl;
    private static ILogPrinter defaultPrinter;
    private static ILogPrinter simplePrinter;
    private static ILogPrinter fullPrinter;
    private static String pre;

    public static synchronized ILogPrinter simple() {
        if (simplePrinter == null) {
            simplePrinter = new SimpleLogPrinter();
        }
        return simplePrinter;
    }

    public static synchronized ILogPrinter full() {
        if (fullPrinter == null) {
            fullPrinter = new SimpleLogPrinter();
        }
        return fullPrinter;
    }

    protected static void init(String pre, ILogPrinter logPrinter, ILogControl iLogControl) {
        LogHelper.pre = pre;
        LogHelper.defaultILogControl = iLogControl;
        LogHelper.defaultPrinter = logPrinter;
    }

    public static LogScheduler instance(String tag) {
        return new LogScheduler(pre + tag, defaultPrinter, defaultILogControl);
    }

    public static LogScheduler instance(String tag, ILogPrinter printer, ILogControl logControl) {
        return new LogScheduler(pre + tag, printer, logControl);
    }

    public static LogScheduler instance(String tag, ILogPrinter logPrinter) {
        return new LogScheduler(pre + tag, logPrinter, defaultILogControl);
    }

    public static LogScheduler instance(String tag, ILogControl logControl) {
        return new LogScheduler(pre + tag, defaultPrinter, logControl);
    }

    private static LogScheduler networkLog;

    public static synchronized LogScheduler network() {
        if (networkLog == null) {
            networkLog = instance("Http");
        }
        return networkLog;
    }

    private static LogScheduler taskLog;

    public static synchronized LogScheduler task() {
        if (taskLog == null) {
            taskLog = instance("task");
        }
        return taskLog;
    }

    private static LogScheduler imgLog;

    public static synchronized LogScheduler img() {
        if (imgLog == null) {
            imgLog = instance("Image");
        }
        return imgLog;
    }

    private static LogScheduler utilLog;

    public static synchronized LogScheduler util() {
        if (utilLog == null) {
            utilLog = instance("util");
        }
        return utilLog;
    }

    private static LogScheduler parseLog;

    public static synchronized LogScheduler parse() {
        if (parseLog == null) {
            parseLog = instance("parse");
        }
        return parseLog;
    }

    public static LogTag getTag(final String tag){
        return new LogTag() {
            @Override
            public String tag() {
                return tag;
            }
        };
    }

    public static boolean printerReady(){
        return defaultPrinter == null;
    }
}
