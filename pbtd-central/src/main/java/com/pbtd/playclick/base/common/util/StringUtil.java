/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pbtd.playclick.base.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * 字符工具类
 *
 * @author huangdiwen
 */
public class StringUtil {

    public static String convertIllegalStrings(String source) {
        source = StringUtils.replace(source, "<", "&lt;");
        source = StringUtils.replace(source, ">", "&gt;");
        return source;
    }
}
