package app.songy.com.global_base.component.imageClip.photoview.log;

/**
 * Description:
 * Created by song on 2018/6/12.
 * email：bjay20080613@qq.com
 */

public class LogManager {
    private static Logger logger = new LoggerDefault();

    public LogManager() {
    }

    public static void setLogger(Logger newLogger) {
        logger = newLogger;
    }

    public static Logger getLogger() {
        return logger;
    }
}
