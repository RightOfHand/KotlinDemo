package app.songy.com.global_base.component.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.songy.com.global_base.R;
import app.songy.com.global_base.common.helper.HtmlHelper;
import app.songy.com.global_base.common.helper.ThreadHelper;


/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午10:29
 */
public class CustomerToast {

    public final static String TEXT = "1";
    public final static String WARNING = "2";
    public final static String SUCCESS = "3";
    public final static String ERROR = "4";

    public CustomerToast(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * application context
     */
    private Context context;
    private Toast toast = null;
    private View textLayout = null;
    private View textImgLayout = null;

    private Toast getToast() {
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context.getApplicationContext());
        return toast;
    }


    private synchronized View makeTextView(String text) {
        if (textLayout == null || !ThreadHelper.isMainThread()) {
            textLayout = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null);
        }
        TextView mText = (TextView) textLayout.findViewById(R.id.toast_text);
        mText.setText(text);
        return textLayout;
    }


    private synchronized View makeTextImgView(String text, String type) {
        if (textImgLayout == null || !ThreadHelper.isMainThread()) {
            textImgLayout = LayoutInflater.from(context).inflate(R.layout.dialog_img_toast, null);
        }
        TextView mText = (TextView) textImgLayout.findViewById(R.id.toast_text);
        ImageView img = (ImageView) textImgLayout.findViewById(R.id.img_hint);
        int res = R.drawable.ic_warning;
        switch (type) {
            case TEXT:
                return makeTextView(text);
            case WARNING:
                res = R.drawable.ic_warning;
                img.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                res = R.drawable.ic_success;
                img.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                img.setVisibility(View.VISIBLE);
                res = R.drawable.ic_warning;
                break;

        }
        img.setImageResource(res);
        mText.setText(HtmlHelper.fromHtml(text));
        return textImgLayout;
    }


    public Toast makeToast(String text) {
        Toast toast = getToast();
        View layout = makeTextView(text);
        toast.setGravity(Gravity.BOTTOM, 0, 90);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        showToast(toast);
        return toast;
    }

    public Toast makeToast(int text) {
        Toast toast = getToast();
        View layout = makeTextView(context.getString(text));
        toast.setGravity(Gravity.BOTTOM, 0, 90);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        showToast(toast);
        return toast;
    }


    public Toast makeImgToast(String text, String type) {
        Toast toast = getToast();
        View layout = makeTextImgView(text, type);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        showToast(toast);
        return toast;
    }

    public Toast makeImgToast(int text, String type) {
        Toast toast = getToast();
        View layout = makeTextImgView(context.getString(text), type);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        showToast(toast);
        return toast;
    }

    private void showToast(final Toast toast) {
        if (ThreadHelper.isMainThread()) {
            toast.show();
        } else {
            ThreadHelper.postMain(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        }
    }

    public void cancel() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        textLayout = null;
        textImgLayout = null;
    }
}
