package app.songy.com.global_base.common.lang;

import android.text.TextUtils;

import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;
/**
 *Description:
 *creator: song
 *Date: 2018/6/7 上午11:12
 */
public final class Strings {
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String DEGREE = "°";
    public static final String EQUAL = "=";
    public static final String AND = "&";
    public static final String SLASH = "/";
    public static final String HTML_SPACE = "&nbsp;";
    public static final String ZERO = "0";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * 换行
     */
    public static final String WRAP = "<br>";

//    public static boolean isBlank(CharSequence text) {
//        if (text == null || text.length() == 0) return true;
//        for (int i = 0, L = text.length(); i < L; i++) {
//            if (!Character.isWhitespace(text.charAt(i))) return false;
//        }
//        return true;
//    }

    public static String trimAll(CharSequence s) {
        if (s == null || s.length() == 0) {
            return Strings.EMPTY;
        }
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0, L = s.length(); i < L; i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean isPhoneNum(String phoneNum) {
        if (phoneNum == null || phoneNum.length() != 11) {
            return false;
        }
        if (!TextUtils.isDigitsOnly(phoneNum)) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        return p.matcher(phoneNum).find();
    }

    public static boolean isTelNum(String num) {
        if (TextUtils.isEmpty(num)) {
            return false;
        }
        Pattern p = Pattern.compile("(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{8}");
        return p.matcher(num).find();
    }

    public static boolean isChinese(String string) {
        boolean flag = false;
        for (int i = 0, L = string.length(); i < L; i++) {
            char c = string.charAt(i);
            if ((c >= 0x4e00) && (c <= 0x9FA5)) {
                flag = true;
            } else {
                return false;
            }
        }
        return flag;
    }

    /**
     * 判断字符长度是否在范围里面。 当start end 为 -1 时，表示字符长度不考虑上线或下线
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static boolean isLengthInRange(String str, int start, int end) {
        boolean isInRange = true;
        int length = str.length();
        if (start != -1 && length < start) {
            isInRange = false;
        }
        if (end != -1 && end < length) {
            isInRange = false;
        }
        return isInRange;
    }

    public static long toLong(String text, long defaultVal) {
        if (TextUtils.isDigitsOnly(text)) {
            try {
                return Long.parseLong(text);
            } catch (NumberFormatException e) {
            }
        }
        return defaultVal;
    }

    public static int toInt(String text, int defaultVal) {
        if (TextUtils.isDigitsOnly(text)) {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
            }
        }
        return defaultVal;
    }

    /**
     * 首字母是否匹配
     */
    public static boolean isAcronymMatch(String name, String keyword) {
        String acronym = getPinYinHeadChar(name);
        return acronym.contains(keyword.toLowerCase()) || acronym.contains(keyword.toUpperCase());
    }

    /**
     * 提取每个汉字的首字母(大写)
     *
     * @param str 数据
     * @return 拼音
     */
    @Deprecated //未实现
    public static String getPinYinHeadChar(String str) {
//        return PinYinUtil.getAllFirstLetter(str);
        return "";
    }

    public static String join(Iterable<?> iterable, String separator) {

        // handle null, zero and one elements before building a buffer
        if (iterable == null) {
            return null;
        }
        Iterator<?> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return toString(first);
        }

        // two or more elements
        StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static boolean toBoolean(String property, boolean defaultVal) {
        return property == null ? defaultVal : Boolean.valueOf(property);
    }

    public static String format(String format, Object... objects) {
        return String.format(Locale.getDefault(), format, objects);
    }

    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str) && !str.matches("^[\\s\\n\\r\\t]*$");
    }

    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }
}
