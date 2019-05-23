package com.bank.system.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author taylor
 * @ClassName: MD5Util
 * @Description: md5加密
 * @date: 2019-05-20 11:40
 */

public class MD5Util {
    private static final String key="taylor";

    /**
     * 加密
     * @param text 明文
     * @return encodeStr 密文
    * @throws Exception
     */
    public static String md5(String text){
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * 验证
     * @param text 明文
     * @param md5 密文
     * @return whether right? true :false
     * @throws Exception
     */
    public static boolean verify(String text, String md5){

        String md5Text =md5(text);
        return (md5Text.equalsIgnoreCase(md5))? true:false;
    }

}
