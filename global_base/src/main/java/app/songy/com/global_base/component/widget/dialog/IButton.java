package app.songy.com.global_base.component.widget.dialog;

import android.view.View;

import java.io.Serializable;

/**
 * @author Created by Xinzai on 2017/4/7.
 */

public interface IButton extends Serializable{
    String getText();
    View.OnClickListener getListener();
}
