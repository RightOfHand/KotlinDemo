package app.songy.com.kotlindemo;

import android.app.Application;
import android.content.Context;

import app.songy.com.global_base.common.base.IApp;
import app.songy.com.global_base.common.helper.InitHelper;
import cn.com.bsfit.dfp.android.FRMS;

/**
 * Description:
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

public class App extends Application implements IApp {
    private static long startTime;
    private Context context;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public boolean isLogD() {
        return false;
    }

    @Override
    public boolean isLogE() {
        return false;
    }

    @Override
    public boolean isBeta() {
        return false;
    }

    @Override
    public boolean isRelease() {
        return false;
    }

    @Override
    public boolean isDebug() {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        startTime = System.currentTimeMillis();
        InitHelper.instance().init(this);
        FRMS.instance().startup(this);
//        FRMS.instance().setURL("https://20.26.217.190:9605/public/downloads/frms-fingerprint.js?custID=sy123&serviceUrl=http(s)://120.26.217.190:9604/public/generate/jsonp&channel=AND&loadSource=script ");
        FRMS.instance().setURL("http://120.26.217.190:9605/public/downloads/frms-fingerprint.js?custID=sy123&serviceUrl=http://120.26.217.190:9605/public/generate/jsonp&channel=AND&loadSource=script");
        FRMS.instance().setCustID("sy123");

    }
}
