package app.songy.com.global_base.component.widget.dialog;

/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:04
 */
interface IDialog {

    void dismiss();

    interface OnCancelListener {
        void onCancel(IDialog dialog);
    }

    interface OnClickListener {
        void onClick(IDialog dialog, int which);
    }

}
