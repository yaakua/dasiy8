package com.daisy8.dian.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2009-9-1
 * Time: 15:44:04
 */
public class URLUtil {
    /**
     * 发出请求
     *
     * @param urlString 要发送的请求
     */
    public static void openURL(String urlString) throws Exception {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            //发送域信息
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            out.write("deleteURL=http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg" +
                    ";http://www.oyangk.com:8080/img/file/img/default/tmp_090901143509.jpg");
            out.flush();
            out.close();
            con.connect();
            is = con.getInputStream();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                throw e;
            }
            if (con != null) {
                con.disconnect();
                con = null;
            }
        }
    }

    /**
     * 发出请求
     * @param urlString 请求路径
     * @param key 参数名
     * @param value 参数值
     * @throws Exception 异常
     */
    public static void postData(String urlString,String key,String value) throws Exception {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            //发送域信息
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            out.write(key+"="+value);
            out.flush();
            out.close();
            con.connect();
            is = con.getInputStream();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (is != null) {
                    is.close();
                    //is = null;
                }
            } catch (IOException e) {
                //throw e;
            }
            if (con != null) {
                con.disconnect();
                //con = null;
            }
        }
    }

    public static void main(String[] args) {
        try {
            URLUtil.openURL("http://www.oyangk.com:8080/img/img/delete.htm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}