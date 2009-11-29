package com.daisy8.dian.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2009-3-7
 * Time: 16:56:49
 * MD5加密
 */
public class MD5Util {

    private static Logger logger = Logger.getLogger(MD5Util.class);
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String getMD5Password(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuffer sb = new StringBuffer(32);
            for (byte b : bytes) {
                sb.append(hexDigits[b >>> 4 & 0xF]);
                sb.append(hexDigits[b & 0xF]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            logger.info("在产生MD5时发生了错误:" + ex);
        }
        return null;
    }

    private MD5Util() {

    }
}