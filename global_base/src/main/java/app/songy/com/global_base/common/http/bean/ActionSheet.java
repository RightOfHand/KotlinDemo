package app.songy.com.global_base.common.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by suyxin on 2017/6/15.
 */

public class ActionSheet implements Serializable {
    public static final int TOAST_HINT = 0;
    public static final int DIALOG_HINT = 1;
    private int tip;//" : 0,               //提示类型，0、弱提示；1、弹框提示
    private String title;//" : "提示标题",   //这个只在弹框的情况下有用
    private String message;//" : "提示内容",
    private List<ActionButton> button;//" : [
    // 最大支持三个按钮，1 和 2 个按钮为横向排列，3 个按钮为竖向排列


    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<ActionButton> getButton() {

        return button;
    }

    public void setButton(List<ActionButton> button) {
        this.button = button;
    }
}
