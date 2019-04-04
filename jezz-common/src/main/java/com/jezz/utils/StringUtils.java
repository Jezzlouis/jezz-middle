package com.jezz.utils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断是否有中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String toString(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(object.getClass().getName() + ":[  ");
        for (Field field : fields) {
            field.setAccessible(true);
            sb.append(field.getName() + " = ");
            try {
                sb.append(field.get(object));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sb.append("  ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 判断是否为空或者为null并且没有空字符窜例如:"  "
     */
    public static boolean isBlankOrNull(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public static boolean isNotBlankOrNull(CharSequence cs) {
        return !isBlankOrNull(cs);
    }

    /**
     * 检查文件夹名称
     */
    public static boolean checkDirName(String dirName) {
        char[] d = dirName.toCharArray();
        char[] c = new char[]{'\\', '/', ':', '*', '?', '"', '<', '>', '|'};
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < c.length; j++) {
                if (d[i] == c[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotEmptyTrim(String ss) {
        return !(ss == null || ss.length() < 1 || ss.trim().length() < 1);
    }

}