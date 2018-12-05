package com.pbtd.playclick.base.common.util;

import java.util.List;

/**
 * 工具类的外观类，只负责将其它工具类暴露，本身不应有任何业务逻辑
 *
 * @author huangdiwen
 */
public class Utils {
    
    public static <T> T random(Class<T> cls) {
        return ObjectUtil.random(cls);
    }

    public static <T> List<T> random(Class<T> cls, int number) {
        return ObjectUtil.random(cls, number);
    }
}
