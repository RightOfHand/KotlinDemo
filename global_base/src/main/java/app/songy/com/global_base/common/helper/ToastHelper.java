package app.songy.com.global_base.common.helper;

import android.widget.Toast;

import app.songy.com.global_base.component.widget.CustomerToast;


/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午10:23
 */
public class ToastHelper {
    private static CustomerToast customerToast;

    static {
        customerToast = InitHelper.instance().customerToast;
    }


    public static Toast makeNormalToast(int textId) {
        return customerToast.makeToast(textId);
    }

    public static Toast makeNormalToast(String text) {
        return customerToast.makeToast(text);
    }

    public static Toast makeToast(int textId) {
        return customerToast.makeImgToast(textId,CustomerToast.SUCCESS);
    }

    public static Toast makeToast(String text) {
        return customerToast.makeImgToast(text,CustomerToast.TEXT);
    }

    public static Toast makeSuccessToast(String text) {
        return customerToast.makeImgToast(text,CustomerToast.SUCCESS);
    }

    public static Toast makeImgToast(int textId) {
        return customerToast.makeImgToast(textId,CustomerToast.WARNING);
    }

    public static Toast makeImgToast(String text) {
        return customerToast.makeImgToast(text,CustomerToast.WARNING);
    }

    public static Toast makeErrToast(int textId) {
        return customerToast.makeImgToast(textId,CustomerToast.ERROR);
    }

    public static Toast makeErrToast(String text) {
        return customerToast.makeImgToast(text,CustomerToast.ERROR);
    }

    public static Toast makeToast(String text, String type) {
        return customerToast.makeImgToast(text,type);
    }

}