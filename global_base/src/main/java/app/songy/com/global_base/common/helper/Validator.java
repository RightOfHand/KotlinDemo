package app.songy.com.global_base.common.helper;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean password(String password) {
        return password.matches("[0-9a-zA-Z]{8,16}") && !password.matches("[0-9]+") && !password.matches("[a-zA-Z]+");
    }

    public static boolean phoneNub(String phoneNub) {

        return phoneNub.matches("[0-9]{11}");
    }

    public static List<String> findMac(String src) {
        List<String> list = new ArrayList<String>();
        if (src == null || "".equals(src)) {
            return list;
        }
        Pattern pattern = Pattern
                .compile("[0-9a-z]{2}:[0-9a-z]{2}:[0-9a-z]{2}:[0-9a-z]{2}:[0-9a-z]{2}:[0-9a-z]{2}");
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            String mac = matcher.group(0);
            if (!("00:00:00:00:00:00".equals(mac) || "02:00:00:00:00:00".equals(mac))) {
                list.add(matcher.group(0));
            }
        }
        return list;
    }

    public static List<String> findMacM(String src) {
        List<String> list = new ArrayList<String>();
        if (src == null || "".equals(src)) {
            return list;
        }
        Pattern pattern = Pattern
                .compile("[0-9a-z]{2}:[0-9a-z]{2}:[0-9a-z]{2}:[0-9a-z]{2}:[0-9a-z]{2}");
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            String mac = matcher.group(0);
            if (!("00:00:00:00:00".equals(mac) || "02:00:00:00:00".equals(mac))) {
                list.add(matcher.group(0));
            }
        }
        return list;
    }

    public static List<String> findColor(String src) {
        List<String> list = new ArrayList<String>();
        if (src == null || "".equals(src)) {
            return list;
        }
        Pattern pattern = Pattern
                .compile("#[0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8}");
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            list.add(matcher.group(0));
        }
        return list;
    }

    /**
     * 去除汉字,归正编码
     */
    public static String replaceHanzi(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }

        /**
         * 归正编码
         */
        byte[] bytes = input.getBytes();
        String info = "";

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] < 0) {
                bytes[i] = 32;
            }
            info = info + new String(new byte[]{bytes[i]});
        }

        /**
         * 去除中文
         */
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(info);
        List<String> inputs = new ArrayList<>();

        if (m.find()) {
            for (int i = 0; i < info.length(); i++) {
                String ever = info.substring(i, i + 1);
                Matcher m1 = p.matcher(ever);
                if (m1.find()) {
                    ever = "";
                }
                inputs.add(ever);
            }

            String inputNew = "";
            for (int i = 0; i < inputs.size(); i++) {
                inputNew = inputNew + inputs.get(i);
            }
            return inputNew.trim();

        }
        return info.trim();
    }

    /**
     * 验证邮箱
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
