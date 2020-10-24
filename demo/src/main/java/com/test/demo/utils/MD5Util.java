package com.test.demo.utils;

import java.security.MessageDigest;
/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:MD5加密
 */
public class MD5Util {

    /**
     * 32位小写加密
     */
    public static String getMD5(String str){
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(str.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16){
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            str = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return str;
    }

}
