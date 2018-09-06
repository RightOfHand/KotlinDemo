package app.songy.com.global_base.component.widget.progress;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import app.songy.com.global_base.R;


/**
 *Description:
 *creator: song
 *Date: 2018/6/12 上午10:31
 */
public class Progress {

    private static Dialog mProgress = null;

    public static Dialog show(Context context, String msg) {
        try {
            dismiss();
            mProgress = new Dialog(context, R.style.LoadingDialog);
            mProgress.setCancelable(false);
            mProgress.setTitle(null);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_progress, null);
            view.setTag(System.currentTimeMillis());
            if (!TextUtils.isEmpty(msg)) {
                ((TextView) view.findViewById(R.id.message)).setText(msg);
            }
            mProgress.setContentView(view);
            mProgress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mProgress;
    }

    public static Dialog show(Context context) {
        return show(context, "");
    }

    public static void dismiss() {
        if (mProgress != null) {
            try {
                mProgress.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mProgress = null;
    }

    public static boolean isShowing() {
        return mProgress != null && mProgress.isShowing();
    }

}
