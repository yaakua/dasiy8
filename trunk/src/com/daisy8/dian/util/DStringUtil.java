package com.daisy8.dian.util;

import java.util.regex.Pattern;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2009-8-25
 * Time: 11:51:58
 * 字符串工具类
 */
public class DStringUtil {
    /**
     * 对字符串当中的JS代码 进行过滤,全部替换为"非法字符"
     *
     * @param str 要过滤的字符串
     * @return 过滤后的字符串
     */
    public static String doFilter(String str) {
        str = Pattern.compile("<script.*?>.*?</script>", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll("****");
        return str;
    }

    /**
     * 是否为正整数不能以0开头
     *
     * @param o 要检测的对象
     * @return boolean
     */
    public static boolean isNumber(Object o) {
        try {
            if (o != null) {
                String number = (String) o;
                StringBuffer sb = new StringBuffer();
                sb.append("^[1-9][0-9]*");
                sb.append("+$");
                return Pattern.matches(sb.toString(), number);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否为钱币的格式，可以是50 或50.5 或50.15
     *
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {
        try {
            if (str != null) {
                StringBuffer sb = new StringBuffer();
                sb.append("^(?!0\\d)(?!\\.)[0-9]+(\\.[0-9]{1,2})?$");
                return Pattern.matches(sb.toString(), str);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要判断的字符串
     * @return 为空则为true
     */
    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str.trim()));
    }

    public static String subString(String str) {

        return str;
    }

    /**
     * 从钱的格式的字符串中获取*100后的整数
     * @param str
     * @return
     */
    public static int getMoneyFromStr(String str) {
        if (DStringUtil.isMoney(str)) {
            float f = Float.parseFloat(str);
            float c;
            int b;
            c = f % 1;
            b = (int) (f - c);
            b *= 100;
            c *= 100;
            b = b + (int) c;
            return b;
        }else{
            return 0;
        }
    }



    private static String getContent(Map params, String privateKey) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr + privateKey;
    }

  /*  public static void main(String[] args) {
        String s = "12315";
        float f = Float.parseFloat(s);
        float c = 0;
        int b;
        c = f % 1;
        b = (int) (f - c);
        b *= 100;
        c *= 100;
        b = b + (int) c;
        System.out.println("*******" + b);
    }*/
}