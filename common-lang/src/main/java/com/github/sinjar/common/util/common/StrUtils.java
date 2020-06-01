package com.github.sinjar.common.util.common;

import lombok.NonNull;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Description : </b> 字符串扩展处理类
 * create on 2019/10/10 11:48
 *
 * @author CPF
 **/
public interface StrUtils {

    /**
     * 正则替换, 按照 regex 对 content 进行查询指定字符串, 并在字符串前后增加 prefix 前缀和 suffix 后缀
     * eg: replaceJoinAll("\\d+", "sing34hj32kh423jk", "<", ">");
     * return sing<34>hj<32>kh<42322>jk
     *
     * @param regex 正则表达式
     * @param content 文本
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 处理过的字符串
     */
    static String replaceJoinAll(String regex, String content, String prefix, String suffix){
        final Pattern p = Pattern.compile(regex);
        // 获取 matcher 对象
        final Matcher m = p.matcher(content);
        final StringBuffer sb = new StringBuffer();
        while(m.find()){
            final String group = m.group();
            m.appendReplacement(sb, "");
            sb.append(prefix).append(group).append(suffix);
        }
        m.appendTail(sb);
        return sb.toString();
    }


    /**
     * 首字母变小写
     */
    static String firstCharToLowerCase(@NonNull String str) {
        str = str.trim();
        if (str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 首字母变大写
     */
    static String firstCharToUpperCase(@NonNull String str) {
        str = str.trim();
        if (str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 返回待处理字符串的驼峰格式
     * @param string 待处理的字符串
     * @return 返回驼峰式字符串, 以'_'为分隔符
     */
    static String lowerCamel(@NonNull String string){
        StringTokenizer tokenizer = new StringTokenizer(string);
        StringBuilder sb = null;
        while (tokenizer.hasMoreElements()) {
            String s = tokenizer.nextToken("_").toLowerCase();
            if (sb == null) {
                sb = new StringBuilder();
                sb.append(s);
            } else {
                sb.append(firstCharToUpperCase(s));
            }
        }
        if (sb == null) {
            return "";
        }
        return sb.toString();
    }

    /**
     * 返回待处理字符串的小写下滑线形式
     * @param string 待处理的字符串
     * @return 待处理字符串的小写下滑线形式, 以'_'为分隔符
     */
    static String lowerDownLine(@NonNull String string){
        string = string.trim();
        int len = string.length();
        if (len == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            final char ch = string.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (i > 0) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 返回待处理字符串的驼峰格式
     * @param string 待处理的字符串
     * @return 返回驼峰式字符串, 以'_'为分隔符
     */
    static String upperCamel(@NonNull String string){
        return firstCharToUpperCase(lowerCamel(string));
    }

}
