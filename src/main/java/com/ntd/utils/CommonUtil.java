package com.ntd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by nongtiedan on 2016/8/31.
 */
public class CommonUtil {

    /**
     * 判断一个字符串是否为数字
     */
    public static boolean isNumber1(String str) {
        return str.matches("\\d+");
    }

    /**
     * 判断一个字符串是否为数字
     */
    public static boolean isNumber2(String str){
        for(int i = str.length(); --i>=0; ){
            int chr = str.charAt(i);
            if(chr < 48 || chr > 57)
                return false;

        }

        return true;
    }

    /**
     * 判断一个字符串是否为QQ号
     */
    public static boolean isQQNumber(String num) {
        return Pattern.compile("^[1-9][0-9]{4,11}$").matcher(num).matches();
    }

    /**
     * 验证手机号是否正确
     */
    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{4,8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     */
    public static int getLength2(String text) {
        if (text == null) {
            return 0;
        }
        int totalCount = 0;
        for (int i = 0; i < text.length(); i++) {
            int c = text.codePointAt(i);
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                totalCount++;
            } else {
                totalCount += 2;
            }
        }
        return totalCount;
    }

    /**
     * 获得全角的长度
     */
    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
            {
                length++;
            }
            else{
                length += 2;
            }
        }

        return length;

    }

    /**
     * 判断一个字符串是否为邮箱格式
     */
    public static boolean isEmail(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        String str = "abc@163.com";
        System.out.println(isEmail(str));
        System.out.println(getLength(str));
        System.out.println(getWordCount(str));
    }

}
