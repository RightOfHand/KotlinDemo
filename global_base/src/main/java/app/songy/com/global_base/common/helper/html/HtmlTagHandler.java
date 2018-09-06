package app.songy.com.global_base.common.helper.html;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;


import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.HashMap;

import app.songy.com.global_base.common.base.BaseActivity;
import app.songy.com.global_base.common.helper.ColorHelper;
import app.songy.com.global_base.common.manager.ActivityManager;

/**
 * Created by blue on 2015/8/11
 */
public class HtmlTagHandler implements Html.TagHandler {
    private static String TAG_FONT = "tag_font";

    private int startIndex = 0;
    private int stopIndex = 0;
    private float density = 1;

    public HtmlTagHandler(String tag) {
        TAG_FONT = tag;
        BaseActivity activity = (BaseActivity) ActivityManager.last();
        density = activity != null ? activity.getDensity() : 3;
    }

    final HashMap<String, String> attributes = new HashMap<>();

    @Override
    public void handleTag(boolean opening, String tag, Editable output,
                          XMLReader xmlReader) {
        processAttributes(xmlReader);

        if (tag.equalsIgnoreCase(TAG_FONT)) {
            if (opening) {
                startFont(tag, output, xmlReader);
            } else {
                endFont(tag, output, xmlReader);
            }
        }

    }

    public void startFont(String tag, Editable output, XMLReader xmlReader) {
        startIndex = output.length();
    }

    public void endFont(String tag, Editable output, XMLReader xmlReader) {
        stopIndex = output.length();

        String color = attributes.get("color");
        String size = attributes.get("size");
        if (!TextUtils.isEmpty(size)) {
            size = size.replaceAll("px", "");
        }

        if (!TextUtils.isEmpty(color)) {
            output.setSpan(new ForegroundColorSpan(ColorHelper.parseColor(color)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (!TextUtils.isEmpty(size)) {
            output.setSpan(new AbsoluteSizeSpan(Integer.parseInt(size), true), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    private void processAttributes(final XMLReader xmlReader) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            if (elementField == null) {
                return;
            }
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            if (element == null) {
                return;
            }
            Field attsField = element.getClass().getDeclaredField("theAtts");
            if (attsField == null) {
                return;
            }
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[]) dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer) lengthField.get(atts);

            for (int i = 0; i < len; i++) {
                attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
