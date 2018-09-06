package app.songy.com.global_base.common.log;

import android.support.annotation.NonNull;

import app.songy.com.global_base.common.listener.LogTag;


/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午9:52
 */
public class LogScheduler implements ILogger {
    private String tag = "default";
    private boolean enableD = true;
    private boolean enableE = true;
    private boolean enableI = true;
    private boolean enableW = true;

    private ILogPrinter iLogPrinter;

    public LogScheduler(String tag, ILogPrinter iLogPrinter) {
        this(tag, iLogPrinter, null);
    }

    public LogScheduler(@NonNull String tag, @NonNull ILogPrinter iLogPrinter, ILogControl iLogControl) {
        this.iLogPrinter = iLogPrinter;
        this.tag = tag;
        if (iLogControl != null) {
            enableD = iLogControl.enableD();
            enableE = iLogControl.enableE();
            enableI = iLogControl.enableI();
            enableW = iLogControl.enableW();
        }
    }

    @Override
    public void d(String content, Object... args) {
        if (enableD) {
            if (iLogPrinter != null) {
                iLogPrinter.printD(tag, content, args);
            }
//            StackTraceElement targetStackTraceElement = getTargetStackTraceElement();
//            iLogPrinter.printD(tag, "code index：(%s:%s)",targetStackTraceElement.getFileName(),
//                    targetStackTraceElement.getLineNumber() );
        }
    }


    @Override
    public void d(LogTag logTag, String content, Object... args) {
        if (enableD) {
            if (iLogPrinter != null) {
                iLogPrinter.printD(tag + logTag.tag(), content, args);
            }
//            StackTraceElement targetStackTraceElement = getTargetStackTraceElement();
//            iLogPrinter.printD(tag + logTag.tag(), "code index：(%s:%s)",targetStackTraceElement.getFileName(),
//                    targetStackTraceElement.getLineNumber() );
        }
    }

    @Override
    public void e(String content, Object... args) {
        if (enableE) {
            if (iLogPrinter != null) {
                iLogPrinter.printE(tag, content, args);
            }
        }
    }

    @Override
    public void e(LogTag logTag, String content, Object... args) {
        if (enableE) {
            if (iLogPrinter != null) {
                iLogPrinter.printE(tag + logTag.tag(), content, args);
            }
        }
    }

    @Override
    public void e(Throwable throwable) {
        if (enableE) {
            if (iLogPrinter != null) {
                iLogPrinter.printE(tag, throwable, throwable.getMessage());
            }
        }
    }

    @Override
    public void e(LogTag logTag, Throwable throwable) {
        if (enableE) {
            if (iLogPrinter != null) {
                iLogPrinter.printE(tag + logTag.tag(), throwable, throwable.getMessage());
            }
        }
    }

    @Override
    public void e(Throwable throwable, String content, Object... args) {
        if (enableE) {
            iLogPrinter.printE(tag, throwable, content, args);
        }
    }

    @Override
    public void e(LogTag logTag, Throwable throwable, String content, Object... args) {
        if (enableE) {
            iLogPrinter.printE(tag + logTag.tag(), throwable, content, args);
        }
    }

    @Override
    public void i(String content, Object... args) {
        if (enableI) {
            iLogPrinter.printI(tag, content, args);
        }
    }

    @Override
    public void i(LogTag logTag, String content, Object... args) {
        if (enableI) {
            iLogPrinter.printI(tag + logTag.tag(), content, args);
        }
    }

    @Override
    public void w(String content, Object... args) {
        if (enableW) {
            iLogPrinter.printW(tag, content, args);
        }
    }

    @Override
    public void w(LogTag logTag, String content, Object... args) {
        if (enableW) {
            iLogPrinter.printW(tag + logTag.tag(), content, args);
        }
    }
    private StackTraceElement getTargetStackTraceElement() {
        // find the target invoked method
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
//            boolean isLogMethod = stackTraceElement.getClassName().equals(LogScheduler.class.getName());
            boolean isLogMethod = stackTraceElement.getFileName().contains("Activity") || stackTraceElement.getFileName().contains("Fragment");
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }
}
