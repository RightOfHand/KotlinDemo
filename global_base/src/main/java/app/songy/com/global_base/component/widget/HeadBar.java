package app.songy.com.global_base.component.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.songy.com.global_base.R;
import app.songy.com.global_base.common.base.BaseActivity;
import app.songy.com.global_base.common.manager.ActivityManager;

/**
 *Description:
 *creator: song
 *Date: 2018/6/12 上午11:03
 */
public class HeadBar extends LinearLayout {

    private LinearLayout mToolbar;
    private RelativeLayout mBarContent;
    private TextView mTitle;
    private ImageButton mBack;
    private LinearLayout mRight;
    private View barView, divider;
    private boolean needSetBar = false;
    private ImageButton close;

    public HeadBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public HeadBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public HeadBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        setBackgroundResource(R.color.transparent);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.head_bar, this);
        mToolbar = (LinearLayout) findViewById(R.id.tool_bar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mBack = (ImageButton) findViewById(R.id.toolbar_back);
        mRight = (LinearLayout) findViewById(R.id.toolbar_right);
        mBarContent = (RelativeLayout) findViewById(R.id.toolbar_content);
        barView = findViewById(R.id.toolbar_bar_view);
        divider = findViewById(R.id.toolbar_divider);
        close = (ImageButton) findViewById(R.id.toolbar_close);
        if (mBack != null) {
            mBack.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (getContext() instanceof BaseActivity) {
                        ((BaseActivity) getContext()).onBackPressed();
                    }
                }
            });
        }
        divider.setVisibility(GONE);

//        if (getContext() instanceof BaseActivity)
//            StatusBarUtil.StatusBarLightMode((BaseActivity) getContext());
    }

    public void setToolBarColor(@ColorRes int color) {
        if (mToolbar != null) {
            mBarContent.setBackgroundResource(color);
            mToolbar.setBackgroundResource(color);
            barView.setBackgroundResource(color);
            setBackgroundResource(color);
        }
    }

    public void setBarColor(int color) {
        if (mToolbar != null) {
            mBarContent.setBackgroundColor(color);
            mToolbar.setBackgroundColor(color);
            barView.setBackgroundColor(color);
            setBackgroundColor(color);
        }
    }


    public void setTooBarBackSrc(int src) {
        if (mToolbar != null) {
            mBack.setImageResource(src);
        }
    }

    public void setDividerVisible(int visible) {
        if (divider != null) {
            divider.setVisibility(visible);
        }
    }

    public void setTitle(int titleId) {
        if (mTitle != null) {
            mTitle.setText(titleId);
        }
//        if (needSetBar) setStatusHeightMargin();
    }

    public void setTitle(int titleId, boolean needSetBar) {
        this.needSetBar = needSetBar;
        setTitle(titleId);
    }

    public void setTitle(CharSequence title, boolean needSetBar) {
        this.needSetBar = needSetBar;
        setTitle(title);
    }

    public CharSequence getTitle() {

        return mTitle != null ? mTitle.getText().toString().trim() : "";

    }

    public void setTitle(CharSequence title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
//        setStatusHeightMargin();
    }

    public TextView getTitleView() {
        return mTitle;
    }


    public void setTitleTextColor(int textColor) {
        if (mTitle != null) {
            mTitle.setTextColor(ContextCompat.getColor(getContext(), textColor));
        }
    }

    /**
     * TODO 添加右图标
     *
     * @param drawable 资源文件
     * @param listener 监听器
     */
    public ImageButton addImage(int drawable, OnClickListener listener) {
        ImageButton imageView = null;
        if (mRight != null) {
            imageView = new ImageButton(getContext());
            imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            imageView.setPadding(getDimen(R.dimen.margin_normal), 0, getDimen(R.dimen.margin_normal), 0);
            imageView.setImageResource(drawable);
            imageView.setOnClickListener(listener);
            imageView.setBackgroundResource(R.color.transparent);
            mRight.addView(imageView);
        }
        return imageView;
    }

    /**
     * TODO 添加右图标
     *
     * @param url      资源文件
     * @param listener 监听器
     */
    public ImageButton addImage(String url, OnClickListener listener) {
        ImageButton imageView = null;
        if (mRight != null) {
            imageView = new ImageButton(getContext());
            imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            imageView.setPadding(getDimen(R.dimen.margin_normal), 0, getDimen(R.dimen.margin_normal), 0);
            imageView.setOnClickListener(listener);
            imageView.setBackgroundResource(R.color.transparent);
            //load image
            mRight.addView(imageView);
        }
        return imageView;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     资源文件(String)
     * @param listener 监听器
     */
    public TextView addText(@StringRes int text, OnClickListener listener) {
        TextView textView = null;
        if (mRight != null) {
            textView = new TextView(getContext());
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            textView.setMinWidth(getDimen(R.dimen.btn_height));
            textView.setPadding(getDimen(R.dimen.margin_normal), 0,
                    getDimen(R.dimen.margin_middle), 0);
            textView.setGravity(Gravity.CENTER);
            textView.setText(text);
            textView.setTextSize(16);
            textView.setTextColor(getResources().getColor(R.color.grid_text));
            textView.setOnClickListener(listener);
//            textView.setBackgroundResource(R.drawable.item_selector_black);
            mRight.addView(textView);
        }
        return textView;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     资源文件(String)
     * @param bg       背景资源文件
     * @param listener 监听器
     */
    public TextView addText(int text, @DrawableRes int bg, OnClickListener listener) {
        TextView textView = null;
        if (mRight != null) {
            textView = new TextView(getContext());
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            textView.setMinWidth(getDimen(R.dimen.btn_height));
            textView.setGravity(Gravity.CENTER);
            textView.setText(text);
            textView.setTextSize(16);
            textView.setTextColor(getResources().getColor(R.color.grid_text));
            textView.setOnClickListener(listener);
            textView.setBackgroundResource(bg);
            mRight.setPadding(0, 0, getDimen(R.dimen.margin_normal), 0);
            mRight.addView(textView);
        }
        return textView;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     资源文件(String)
     * @param bg       背景资源文件
     * @param listener 监听器
     */
    public TextView addText(String text, @DrawableRes int bg, OnClickListener listener) {
        TextView textView = null;
        if (mRight != null) {
            textView = new TextView(getContext());
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            textView.setMinWidth(getDimen(R.dimen.btn_height));
            textView.setGravity(Gravity.CENTER);
            textView.setText(text);
            textView.setTextSize(16);
            textView.setTextColor(getResources().getColor(R.color.grid_text));
            textView.setOnClickListener(listener);
            textView.setBackgroundResource(bg);
            mRight.setPadding(0, 0, getDimen(R.dimen.margin_normal), 0);
            mRight.addView(textView);
        }
        return textView;
    }

    public View getClose() {
        return close;
    }

    /**
     * TODO 添加右文字
     *
     * @param text     字符串
     * @param listener 监听器
     */
    public TextView addText(String text, OnClickListener listener) {
        TextView textView = null;
        if (mRight != null) {
            textView = new TextView(getContext());
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            textView.setMinWidth(getDimen(R.dimen.btn_height));
            textView.setPadding(getDimen(R.dimen.margin_normal), 0,
                    getDimen(R.dimen.margin_middle_normal), 0);
            textView.setGravity(Gravity.CENTER);
            textView.setText(text);
            textView.setTextSize(16);
            textView.setTextColor(getResources().getColor(R.color.grid_text));
            textView.setOnClickListener(listener);
//            textView.setBackgroundResource(R.drawable.item_selector);
            mRight.addView(textView);
        }
        return textView;
    }

    public void setRightView(View view, OnClickListener listener) {
        setRightView(view, null, listener);
    }

    public void setRightView(View view, LayoutParams lp, OnClickListener listener) {
        if (mRight != null) {
            if (lp == null) {
                mRight.addView(view);
            } else {
                mRight.addView(view, lp);
            }
            mRight.setOnClickListener(listener);
        }
    }

    public void setStatusHeightMargin() {
        if (barView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        BaseActivity activity = (BaseActivity) ActivityManager.last();
        if (activity == null) {
            return;
        }
//        LayoutParams params = new LayoutParams(barView.getLayoutParams());
//        params.height = activity.getStatusHeight();
        barView.setVisibility(GONE);
//        barView.setLayoutParams(params);
//        if (getContext() instanceof BaseActivity)
//            StatusBarUtil.StatusBarLightMode((BaseActivity) getContext());
    }

    public void setStatusBarBlack() {
//        if (getContext() instanceof BaseActivity)
//            StatusBarUtil.StatusBarLightMode((BaseActivity) getContext());
    }

    private int getDimen(@DimenRes int dimen) {
        return getResources().getDimensionPixelOffset(dimen);
    }

    public void setBackVisible(int backVisible) {
        if (mBack != null) {
            mBack.setVisibility(backVisible);
        }
    }

    public View getBackView() {
        return mBack;
    }

    public ViewGroup getRightView() {
        return mRight;
    }

    public void setBackIcon(@DrawableRes int backIcon) {
        if (mBack != null) {
            mBack.setImageResource(backIcon);
        }
    }

    public void setBarVisible(int visible) {
        if (mBarContent != null) {
            mBarContent.setVisibility(visible);
        }
    }

}
