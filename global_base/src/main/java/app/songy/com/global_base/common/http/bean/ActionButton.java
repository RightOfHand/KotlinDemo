package app.songy.com.global_base.common.http.bean;

import android.view.View;

import java.io.Serializable;

/**
 *Description: respone->actionSheet->button
 *creator: song
 *Date: 2018/6/7 上午10:10
 */

public class ActionButton implements Serializable {
    private String title;//" : "确定",
    private String color;//" : "#FFFFFF",
    private String action;//" : "dmkj://****"
    // 按钮对应功能，如果没有 action 就直接取消当前弹框

    private View.OnClickListener listener;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
