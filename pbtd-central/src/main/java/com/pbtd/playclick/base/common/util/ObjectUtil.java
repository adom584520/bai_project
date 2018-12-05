/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pbtd.playclick.base.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.io.IOException;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 对象工具类
 *
 * @author huangdiwen
 */
public class ObjectUtil {

    @SuppressWarnings("rawtypes")
	public static <T> T random(Class<T> cls) {
        try {
            T obj = cls.newInstance();
            BeanInfo info = Introspector.getBeanInfo(cls);
            MethodDescriptor[] descriptors = info.getMethodDescriptors();
            for (MethodDescriptor descriptor : descriptors) {
                if (descriptor.getName().startsWith("set")) {
                    Class[] parameterClssses = descriptor.getMethod().getParameterTypes();
                    if (parameterClssses != null && parameterClssses.length == 1) {
                        Class parameterCls = parameterClssses[0];
                        Object parameter = randomParameter(parameterCls);
                        descriptor.getMethod().invoke(obj, parameter);
                    }
                }
            }

            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> List<T> random(Class<T> cls, int number) {
        try {
            List<T> list = new ArrayList<T>();
            int count = 0;
            while (count++ < number) {
                list.add(random(cls));
            }
            return list;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String randomString(boolean letters, boolean digits, boolean chinese) {
        String value = (letters ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "")
                + (digits ? "0123456789" : "")
                + (chinese ? "欢迎你我是中文随机测试" : "");
        int len = (int) (Math.random() * 10);
        len = Math.max(6, len);

        StringBuilder builder = new StringBuilder();
        int count = 0;
        while (count++ < len) {
            int index = (int) (Math.random() * value.length());
            index = Math.min(value.length() - 1, index);
            index = Math.max(0, index);
            builder.append(value.charAt(index));
        }
        return builder.toString();
    }

    @SuppressWarnings("rawtypes")
	private static Object randomParameter(Class parameterCls) throws IOException {
        if (parameterCls == String.class) {
            String value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789欢迎你我是中文随机测试";
            int len = (int) (Math.random() * 10);
            len = Math.max(1, len);

            StringBuilder builder = new StringBuilder();
            int count = 0;
            while (count++ < len) {
                int index = (int) (Math.random() * value.length());
                index = Math.min(value.length() - 1, index);
                index = Math.max(0, index);
                builder.append(value.charAt(index));
            }
            return builder.toString();
        } else if (parameterCls == Integer.class) {
            return (int) (Math.random() * 10000);
        } else if (parameterCls == Date.class) {
            return new Date();
        } else if (parameterCls == Boolean.class) {
            return Math.random() > 0.5 ? true : false;
        } else if (parameterCls == Clob.class) {
            return null;
        } else {
            throw new UnsupportedOperationException("Unsupported " + parameterCls.getName());
        }
    }

    @SuppressWarnings("rawtypes")
	public static Object convert(String value, Class cls) {
        if (cls == String.class) {
            return isEmpty(value) ? null : value;
        } else if (cls == Boolean.class) {
            return isEmpty(value) ? null : Boolean.valueOf(value);
        } else if (cls == boolean.class) {
            return isEmpty(value) ? false : Boolean.valueOf(value).booleanValue();
        } else if (cls == Integer.class) {
            return isEmpty(value) ? null : Integer.valueOf(value);
        } else if (cls == int.class) {
            return isEmpty(value) ? 0 : Integer.valueOf(value).intValue();
        } else if (cls == Long.class) {
            return isEmpty(value) ? null : Long.valueOf(value);
        } else if (cls == long.class) {
            return isEmpty(value) ? 0L : Long.valueOf(value).longValue();
        } else if (cls == Double.class) {
            return isEmpty(value) ? null : Double.valueOf(value);
        } else if (cls == double.class) {
            return isEmpty(value) ? 0D : Double.valueOf(value).doubleValue();
        } else if (cls == Float.class) {
            return isEmpty(value) ? null : Float.valueOf(value);
        } else if (cls == Float.class) {
            return isEmpty(value) ? 0F : Float.valueOf(value).floatValue();
        } else if (cls == Date.class) {
            return isEmpty(value) ? null : new Date(Long.valueOf(value));
        } else {
            throw new UnsupportedOperationException("暂时未支持的转换类型: " + cls.getName());
        }
    }
    
    private static boolean isEmpty(String value){
       return value == null || value.trim().length() == 0;
    }
}
