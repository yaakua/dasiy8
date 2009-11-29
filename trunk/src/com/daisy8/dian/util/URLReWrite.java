package com.daisy8.dian.util;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2009-9-7
 * Time: 16:31:19
 */
public class URLReWrite {
    private StringBuffer sb = new StringBuffer();

    /**
     * 在URL当中添加参数
     * @param s 要添加的参数
     */
    public void addObject(String s) {
        sb.append("-");
        sb.append(s);
    }

    /**
     * 返回最后生成的URL
     * 形式为：-a-b-c.html 后面.html自动添加
     * @return URL
     */
    public String getReWriteURL() {
        sb.append(".html");
        return sb.toString();
    }
}