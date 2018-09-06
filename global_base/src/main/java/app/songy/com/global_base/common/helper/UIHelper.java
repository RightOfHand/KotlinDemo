package app.songy.com.global_base.common.helper;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * @author Created by xinzai on 2015/5/14.
 */
public class UIHelper {
    private static int deviceWidth = 0;
    private static int deviceHeight = 0;
    private static int times = 0;


    /**
     * TODO(在使用时ListView item必须不包含RelativeLayout(layout中必须重写measure()))
     *
     * @deprecated TODO(谨慎使用)
     **/
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    /**
     * 屏幕宽度
     */
    public static int getDeviceWidth(Context context) {
        if (context != null) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        }
        return 0;
    }

    /**
     * 屏幕高度
     */
    public static int getDeviceHeight(Context context) {
        if (context != null) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        }
        return 0;
    }

    public static DisplayMetrics getMetrics() {
        return AppHelper.getResources().getDisplayMetrics();
    }

    public static int getScreenDPI(Activity mActivity) {
        return mActivity.getResources().getDisplayMetrics().densityDpi;
    }


    public static int getViewMeasuredHeight(TextView tv) {
        tv.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return tv.getMeasuredHeight();

    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean hasEmpty(List<TextView> edits) {
        boolean has = false;
        for (TextView editText : edits) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                return true;
            }
        }
        return has;
    }


    public static boolean hasEmpty(ImageView[] edits) {
        boolean has = false;
        for (ImageView imageView : edits) {
            if (TextUtils.isEmpty(imageView.getTag().toString().trim())) {
                return true;
            }
        }
        return has;
    }

    public static boolean isNull(List objs) {
        if (objs == null || objs.isEmpty()) {
            return true;
        }
        for (Object o : objs) {
            if (o == null) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNull(Object[] objs) {
        if (objs == null || objs.length == 0) {
            return true;
        }
        for (Object o : objs) {
            if (o == null) {
                return true;
            }
        }

        return false;
    }


    public static void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }


    /**
     * 检查当前网络是否可用
     */

    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    Log.d("isNetwork: ", i + "===状态===" + networkInfo[i].getState());
                    Log.d("isNetwork: ", i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static void setRightDrawable(Context context, TextView textView, int draw) {
        Drawable drawable = context.getResources().getDrawable(draw);
        try {
            assert drawable != null;
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void setTopDrawable(Context context, TextView textView, int draw) {
        Drawable drawable = context.getResources().getDrawable(draw);
        try {
            assert drawable != null;
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, drawable, null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public static void rotateView(View view, boolean rotateBack) {
        int duration = 200;
        ObjectAnimator animator;
        if (rotateBack) {
            animator = ObjectAnimator.ofFloat(view, "rotation", 0, 90).setDuration(duration);
            animator.setupStartValues();
        } else {
            animator = ObjectAnimator.ofFloat(view, "rotation", 90, 0).setDuration(duration);
            animator.setupStartValues();
        }
        animator.start();
    }


    /**
     * 系统震动
     *
     * @param context
     */
    public static void startVibrator(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);//震动半秒
    }


    public static boolean hasEmpty(TextView[] edits) {
        for (TextView editText : edits) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下载文件
     *
     * @param context
     * @param url
     */
    public static void downloadFile(Activity context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 判断URL合法性
     */
    public static boolean isUrl(String url) {
        return url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https");
    }




    public static void hideSoftInput(Activity context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    private static float density = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2px(float dpValue) {

        return (int) (dpValue * density + 0.5f);
    }

}
