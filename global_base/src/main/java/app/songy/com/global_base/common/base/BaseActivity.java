package app.songy.com.global_base.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.songy.com.global_base.R;
import app.songy.com.global_base.common.constants.Logs;
import app.songy.com.global_base.common.helper.HtmlHelper;
import app.songy.com.global_base.common.manager.ActivityManager;
import app.songy.com.global_base.component.widget.HeadBar;

/**
 * Description:
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

public class BaseActivity extends AppCompatActivity{
    private float mDensity;
    //回调监听分发
    private List<ActivityResultCallback> mActivityResultCallbacks = null;
    private List<ActivityResumeCallback> mActivityResumeCallbacks = null;
    private List<ActivityPauseCallback> mActivityPauseCallbacks = null;
    private List<ActivityStopCallback> mActivityStopCallbacks = null;
    private List<PermissionCallBack> permissionCallBacks = null;
    private List<ActivityDestroyCallback> mActivityDestroyCallbacks = null;
    //标题栏组件
    private boolean needSetBar = false;
    private HeadBar headBar;
    private boolean animation = true;//页面也换动画标志
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.create(this);
        if (mDensity == 0) {
            initDensity();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        headBar = findViewById(R.id.head_bar_include);


    }
    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        if (mActivityResumeCallbacks != null) {
            for (ActivityResumeCallback callback : mActivityResumeCallbacks) {
                callback.onActivityResume();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mActivityStopCallbacks != null) {
            for (ActivityStopCallback callback : mActivityStopCallbacks) {
                callback.onActivityStop();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mActivityPauseCallbacks != null) {
            for (ActivityPauseCallback callback : mActivityPauseCallbacks) {
                callback.onActivityPause();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.destroy(this);
        if (mActivityDestroyCallbacks != null)
            for (ActivityDestroyCallback callback : mActivityDestroyCallbacks) {
                if (callback != null) {
                    callback.onActivityDestroy();
                }
            }

    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overrideOpenAnim();
    }

    private void logPageInfo(Intent intent) {
        if (intent != null && intent.getComponent() != null
                && intent.getComponent().getClassName() != null) {
            String targetClassName = intent.getComponent().getClassName();
            if (targetClassName.contains(".")) {
                targetClassName = targetClassName.substring(targetClassName.lastIndexOf(".") + 1, targetClassName.length());
            }
            Logs.base.d("启动页面  %s\n到达页面  %s",
                    this.getClass().getSimpleName(),
                    targetClassName);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overrideOpenAnim();
        logPageInfo(intent);
    }

    @Override
    public void finish() {
        // BaseManager.cancelAll();
        super.finish();
        overrideCloseAnim();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mActivityResultCallbacks != null) {
            for (ActivityResultCallback callback : mActivityResultCallbacks) {
                callback.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void addActivityResultCallback(ActivityResultCallback callback) {
        if (mActivityResultCallbacks == null) {
            mActivityResultCallbacks = new ArrayList<>();
        }

        if (mActivityResultCallbacks.size() >= 1) {
            mActivityResultCallbacks.clear();
        }

        mActivityResultCallbacks.add(callback);
    }

    public void removeActivityResultCallback(ActivityResultCallback callback) {
        if (mActivityResultCallbacks != null) {
            mActivityResultCallbacks.remove(callback);
        }
    }

    /**
     * TODO 初始化Density
     */
    private void initDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
    }

    /**
     * TODO 添加Activity权限申请回掉
     */
    public void addRequestPermissionsResult(PermissionCallBack callback) {
        if (permissionCallBacks == null) {
            permissionCallBacks = new ArrayList<>();
        }

        if (permissionCallBacks.size() >= 1) {
            permissionCallBacks.clear();
        }
        permissionCallBacks.add(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionCallBacks == null) {
            return;
        }
        for (PermissionCallBack callBack : permissionCallBacks) {
            callBack.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * TODO 获取当前设备的density
     *
     * @return 当前density
     */
    public float getDensity() {
        return mDensity;
    }

    /**
     * TODO ActivityResult回掉拦截分发接口
     */
    public interface ActivityResultCallback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    /**
     * TODO ActivityResume回调拦截分发接口
     */
    public interface ActivityResumeCallback {
        void onActivityResume();
    }

    /**
     * TODO ActivityStop回调拦截分发接口
     */
    public interface ActivityStopCallback {
        void onActivityStop();
    }

    /**
     * TODO ActivityPause回调拦截分发接口
     */
    public interface ActivityPauseCallback {
        void onActivityPause();
    }

    public interface ActivityDestroyCallback {
        void onActivityDestroy();
    }

    /**
     * TODO 权限回调接口
     */
    public interface PermissionCallBack {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                        @NonNull int[] grantResults);
    }

    /**
     * TODO 设置标题
     *
     * @param needSetBar 需要设置Bar
     * @param titleId    标题ID
     */
    public void setTitle(int titleId, boolean needSetBar) {
        this.needSetBar = needSetBar;
        setTitle(titleId);
    }

    protected void setDividerVisible(int visible) {
        if (headBar != null) {
            headBar.setDividerVisible(visible);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if (headBar != null) {
            headBar.setTitle(titleId, needSetBar);
            super.setTitle("");
        } else {
            super.setTitle(titleId);
        }
    }

    public void setTitle(CharSequence title, boolean needSetBar) {
        this.needSetBar = needSetBar;
        setTitle(HtmlHelper.fromHtml(title + ""));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (headBar != null) {
            headBar.setTitle(HtmlHelper.fromHtml(title + ""));
            super.setTitle("");
        } else {
            super.setTitle(HtmlHelper.fromHtml(title + ""));
        }
    }

    /**
     * TODO 获取HeadTitle
     */
    public CharSequence getHeadTitle() {
        return headBar != null ? headBar.getTitle() : super.getTitle();
    }

    public TextView getTitleView() {
        return headBar.getTitleView();
    }

    public void setVisibleRight(int vis) {
        switch (vis) {
            case View.VISIBLE:
            case View.INVISIBLE:
            case View.GONE:
                getRightView().setVisibility(vis);
                break;
            default:
        }
    }

    /**
     * 设置标题颜色
     *
     * @param textColor
     */
    public void setTitleTextColor(int textColor) {
        if (headBar != null) {
            headBar.setTitleTextColor(textColor);
        }
    }

    /**
     * TODO 添加右图标
     *
     * @param drawable 资源文件
     * @param listener 监听器
     */
    protected ImageButton addImage(int drawable, View.OnClickListener listener) {
        if (headBar != null) {
            return headBar.addImage(drawable, listener);
        }
        return null;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     资源文件(String)
     * @param bg       背景资源文件
     * @param listener 监听器
     */
    protected TextView addText(int text, @DrawableRes int bg, View.OnClickListener listener) {
        if (headBar != null) {
            return headBar.addText(text, bg, listener);
        }
        return null;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     资源文件(String)
     * @param bg       背景资源文件
     * @param listener 监听器
     */
    protected TextView addText(String text, @DrawableRes int bg, View.OnClickListener listener) {
        if (headBar != null) {
            return headBar.addText(text, bg, listener);
        }
        return null;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     资源文件(String)
     * @param listener 监听器
     */
    protected TextView addText(String text, View.OnClickListener listener) {
        if (headBar != null) {
            return headBar.addText(text, listener);
        }
        return null;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     资源文件(String)
     * @param listener 监听器
     */
    protected TextView addText(int text, View.OnClickListener listener) {
        if (headBar != null) {
            return headBar.addText(text, listener);
        }
        return null;
    }

    /**
     * TODO 获取返回的视图
     */
    protected View getBackView() {
        if (headBar != null) {
            return headBar.getBackView();
        }
        return null;
    }

    /**
     * TODO 获取右侧区域视图
     */
    protected ViewGroup getRightView() {
        return headBar.getRightView();
    }

    /**
     * 设置标题栏背景色
     *
     * @param color 色码
     */
    protected void setToolBarColor(@ColorRes int color) {
        if (headBar != null) {
            headBar.setToolBarColor(color);
        }

    }

    protected void setTooBarBackSrc(int src) {
        if (headBar != null) {
            headBar.setTooBarBackSrc(src);
        }
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }

    /**
     * TODO 进入Activity动画
     */
    protected void overrideOpenAnim() {
        if (animation) {
            overridePendingTransition(R.anim.in_from_right, R.anim.fade_out);
        } else {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    /**
     * TODO 退出Activity动画
     */
    protected void overrideCloseAnim() {
        if (animation) {
            overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        } else {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

}
