package app.songy.com.global_base.component.widget.dialog;

import android.view.View;

import static app.songy.com.global_base.common.helper.AppHelper.getString;

/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:25
 */

public class Btn implements IButton {

    private String btn;
    private View.OnClickListener clickListener;

    public Btn(int btn, View.OnClickListener clickListener) {
        this.btn = getString(btn);
        this.clickListener = clickListener;
    }

    public Btn(String btn, View.OnClickListener clickListener) {
        this.btn = btn;
        this.clickListener = clickListener;
    }

    public Btn(int btn) {
        this.btn = getString(btn);
    }


    @Override
    public String getText() {
        return btn;
    }

    @Override
    public View.OnClickListener getListener() {
        return clickListener;
    }
}
