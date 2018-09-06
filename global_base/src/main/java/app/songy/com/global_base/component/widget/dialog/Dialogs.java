package app.songy.com.global_base.component.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.songy.com.global_base.R;


/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:22
 */
public class Dialogs {


    public static Dialog showDialog(Context context, String title, String content, int gravity, Btn... btns) {
        final CommonDialog dialog = new CommonDialog(context, R.style.Dialog);
        dialog.setCancelable(btns == null);
        dialog.setTitle(title);
        dialog.setContent(content);
        dialog.setButtons(btns);
        dialog.setGravity(gravity);
        dialog.show();
        return dialog;
    }

    public static Dialog showConfirmDialog(final Context context, String title, String message, final View.OnClickListener listener) {
        final CommonDialog dialog = new CommonDialog(context, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setContent(message);
        dialog.setTitleGravity(Gravity.CENTER);
        dialog.setGravity(Gravity.CENTER);
        dialog.setButtons(new Btn(R.string.cancel), new Btn(R.string.ok, listener));
        dialog.show();
        return dialog;
    }

    public static Dialog showMustConfirmDialog(final Context context, String title, String message, final View.OnClickListener listener) {
        final CommonDialog dialog = new CommonDialog(context, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(title);
        dialog.setContent(message);
        dialog.setGravity(Gravity.CENTER);
        dialog.setTitleGravity(Gravity.CENTER);
        dialog.setButtons(new Btn(R.string.ok, listener));
        dialog.show();
        return dialog;
    }

    public static <T> Dialog showListDialog(Context context, String title, final List<T> content,
                                            final OnSelectListener<T> onSelectListener) {
        ListDialog<T> dialog = new ListDialog<T>(context, content) {
            @Override
            public void onSelected(T object, int position) {
                if (onSelectListener != null) {
                    onSelectListener.onSelect(position, object);
                }
            }

            @Override
            public String getLabel(T object) {
                return String.valueOf(object);
            }
        };
        dialog.setTitle(title);
        dialog.show();
        return dialog;

    }

    public static  ExtendListDialog showItemsDialog(Context context, String title, final List<KeyValueItem> content,
                                            final OnSelectListener<KeyValueItem> onSelectListener) {
        ExtendListDialog dialog = new ExtendListDialog(context, content) {
            @Override
            public void onSelected(KeyValueItem object, int position) {
                if (onSelectListener != null) {
                    onSelectListener.onSelect(position, object);
                }
            }

        };
        dialog.setTitle(title);
        dialog.show();
        return dialog;

    }
    public static void closeDialog(Dialog dialog) {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Dialog showMapListDialog(Context context, List<HashMap<String, String>> content, final OnSelectListener<Map.Entry<String, String>> listener) {
        if (content==null) {
            content = new ArrayList<>();
        }
        List<Map.Entry<String, String>> mapList = new ArrayList<>();
        for (HashMap<String, String> map : content) {
            mapList.add(getEntry(map));
        }
        final Dialog dialog = new MapListDialog(context, mapList, Gravity.CENTER) {
            @Override
            public void onSelected(Map.Entry<String, String> object, int position) {
                listener.onSelect(position, object);
            }
        };
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    private static Dialog appUpdateDialog;


    public static Map.Entry<String, String> getEntry(Map<String, String> content) {
        for (Map.Entry<String, String> entry : content.entrySet()) {
            return entry;
        }
        return null;
    }


}
