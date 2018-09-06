package app.songy.com.global_base.common.helper;

import android.text.Html;
import android.text.Spanned;

import app.songy.com.global_base.common.helper.html.HtmlTagHandler;
import app.songy.com.global_base.common.lang.Strings;


/**
 * @author Created by Xinzai on 2016/6/30.
 */
public class HtmlHelper {
    public static Spanned fromHtml(String source) {
        if (source == null) {
            source = "";
        }
        source = source.replaceAll("/n", Strings.WRAP);
        if (source.contains("size")) {
            String newStr = "<font></font>" + source;
            String FONT_TAG = "tag_font";
            newStr = newStr.replaceAll("font", FONT_TAG);
            return Html.fromHtml(newStr, null, new HtmlTagHandler(FONT_TAG));
        } else {
            return Html.fromHtml(source);
        }

    }


}
