package app.songy.com.global_base.common.helper;


import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;


import app.songy.com.global_base.common.base.IApp;
import app.songy.com.global_base.common.log.ILogControl;
import app.songy.com.global_base.common.log.ILogPrinter;
import app.songy.com.global_base.component.widget.CustomerToast;


/**
 *Description:
 *creator: song
 *Date: 2018/6/20 下午5:06
 */
public class InitHelper {

    private static InitHelper instance;

    public CustomerToast customerToast;
    public Resources resources;

    public static synchronized InitHelper instance() {
        if (instance == null) {
            instance = new InitHelper();
        }
        return instance;
    }

    public InitHelper init(IApp app) {        /**初始化Toast*/
        customerToast = new CustomerToast(app.getContext());
        initLog(app);
        AppHelper.init(app);
        /**缓存Resources(统一提供)*/
        resources = AppHelper.getResources();
        return this;
    }

    private InitHelper() {

    }




    public static void initLog(final IApp app) {
        ILogPrinter iLog = LogHelper.simple();
        LogHelper.init("sutuo-", iLog, new ILogControl() {
            @Override
            public boolean enableD() {
                return app.isLogD();
            }

            @Override
            public boolean enableE() {
                return app.isLogE();
            }

            @Override
            public boolean enableI() {
                return app.isLogD();
            }

            @Override
            public boolean enableW() {
                return app.isLogD();
            }
        });
    }

    /**
     * 是否在子进程中
     */
    public static boolean isChildProcess(Context context) {
        return !TextUtils.equals(context.getPackageName(), getProgressName(context));
    }

    public static String getProgressName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return null;
    }
}
