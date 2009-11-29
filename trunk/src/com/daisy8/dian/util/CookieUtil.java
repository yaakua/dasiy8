package com.daisy8.dian.util;

import com.chii.util.StringCU;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * 创建人: 阳葵
 * 创建日期: 2009-1-13
 * 创建时间: 15:45:23
 */
public class CookieUtil {
    /**
     * 添加一个cookie值
     *
     * @param name     名称
     * @param value    值
     * @param response 保存cookie的对象
     */
    public static void setCookie(String name, String value, HttpServletResponse response) {
         try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 添加一个cookie值
     *
     * @param name     名称
     * @param value    值
     * @param time     cookie的有效期
     * @param response 保存cookie的对象
     */
    public static void setCookie(String name, String value, Integer time, HttpServletResponse response) {
        if(StringCU.isEmpty(value)){
            return;
        }
        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(time);
        response.addCookie(cookie);
    }

    /**
     * 根据name值,从cookie当中取值
     *
     * @param name    要获取的name
     * @param request cookie存在的对象
     * @return 与name对应的cookie值
     */
    public static String getCookie(String name, HttpServletRequest request) {
        Cookie[] cs = request.getCookies();
        String value = "";
        if (cs != null) {
            for (Cookie c : cs) {
                if (name.equals(c.getName())) {
                    try {
                        value = URLDecoder.decode(c.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                    return value;
                }
            }
        }
        return value;

    }

    /**
     * 删除指定名称的cookie值
     *
     * @param name     要删除的cookie名称
     * @param request  对象
     * @param response 对象
     */
    public static void delCookie(String name, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cs = request.getCookies();
        for (Cookie c : cs) {
            if (name.equals(c.getName())) {
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
        }
    }

    /**
     * 对字符串进行处理(转义字符,特定字符转换)
     *
     * @param str    要转换的字符
     * @param oldStr 被替换的字符
     * @param chars  用于替换的字符
     * @return 处理完成后的字符
     */
    public static String getTranStr(String str, String oldStr, String chars) {
        String tempStr = str.replaceAll("\\r\\n", "<br/>"); //防止设置cookie时字符串过长而自动换行,出现的非法参数
        tempStr = tempStr.replaceAll("\\n", "<br/>");
        if (oldStr != null && chars != null) {  //用于指定字符替换
            tempStr.replaceAll(oldStr, chars);
        }
        return tempStr;
    }
}