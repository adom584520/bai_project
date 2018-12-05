package com.pbtd.manager.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;



public class RequestUtil {

    public static String getString(HttpServletRequest request, String key) {
        return getString(request, key, null);
    }

    public static String getString(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static Integer getInteger(HttpServletRequest request, String key) {
        return getInteger(request, key, null);
    }

    public static Integer getInteger(HttpServletRequest request, String key, Integer defaultValue) {
        String value = request.getParameter(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        } else {
            return new Integer(value);
        }
    }

    public static int getInt(HttpServletRequest request, String key) {
        return getInt(request, key, Integer.MIN_VALUE);
    }

    public static int getInt(HttpServletRequest request, String key, int defaultValue) {
        String value = request.getParameter(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static Double getDouble2(HttpServletRequest request, String key) {
        return getDouble2(request, key, null);
    }

    public static Double getDouble2(HttpServletRequest request, String key, Double defaultValue) {
        String value = request.getParameter(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        } else {
            return new Double(value);
        }
    }

    public static double getDouble(HttpServletRequest request, String key) {
        return getDouble(request, key, Double.MIN_VALUE);
    }

    public static double getDouble(HttpServletRequest request, String key, double defaultValue) {
        String value = request.getParameter(key);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        } else {
            return Double.parseDouble(value);
        }
    }

    private static Date parseDate(String input) {
        Date date = null;
        try {
            date = DateUtils.parseDate(input, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd"});
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 把HttpServletRequest请求参数包装成Map
     *
     * @param request
     * @param emptiable 可空？
     * @return
     */
    public static Map<String, Object> asMap(HttpServletRequest request, boolean emptiable) {
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = ((String) names.nextElement()).trim();
            if (name.equalsIgnoreCase("idField") || name.equalsIgnoreCase("nameField")) {
                continue;
            }
            String[] values = request.getParameterValues(name);
            String key = name.replaceAll("\\[([^\\]]*)\\]", "_$1");
            if (values.length == 1) {
                key = key.replaceAll("_$", "");
                String val = values[0].trim();
                if (val.length() > 0) {
                    Date date = parseDate(val);
                    params.put(key, (date == null ? val : date));
                } else if (emptiable) {
                    params.put(key, "");
                }
            } else {
                boolean isDate = parseDate(values[0].trim()) == null ? (parseDate(values[1].trim()) == null ? false : true) : true;

                List<Object> vals = new ArrayList<Object>();
                for (int i = 0; i < values.length; i++) {
                    String val = values[i].trim();
                    if (val.length() > 0) {
                        Date date = parseDate(val);
                        vals.add(date == null ? val : date);
                    } else if (emptiable || isDate) {
                        vals.add("");
                    }
                }
                if (vals.size() > 0) {
                    params.put(key, vals);
                }
            }
        }
        return params;
    }

    public static Map<String, Object> asMap(HttpServletRequest request) {
        return asMap(request, false);
    }

    /**
     * 把HttpServletRequest请求参数包装成Map
     * @param flag 是否对日期格式进行处理
     * @param request
     * @param emptiable 可空？
     * @return
     */
    public static Map<String, Object> asMap(HttpServletRequest request,boolean flag,boolean emptiable ){
    	Map<String, Object> params = new HashMap<String, Object>();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = ((String) names.nextElement()).trim();
            if (name.equalsIgnoreCase("idField") || name.equalsIgnoreCase("nameField")) {
                continue;
            }
            String[] values = request.getParameterValues(name);
            String key = name.replaceAll("\\[([^\\]]*)\\]", "_$1");
            if (values.length == 1) {
                key = key.replaceAll("_$", "");
                String val = values[0].trim();
                if (val.length() > 0) {
                	if(flag){
                		Date date = parseDate(val);
                		params.put(key, (date == null ? val : date));
                	}else{
                		params.put(key, val);
                	}
                } else if (emptiable) {
                    params.put(key, "");
                }
            } else {
                boolean isDate = parseDate(values[0].trim()) == null ? (parseDate(values[1].trim()) == null ? false : true) : true;

                List<Object> vals = new ArrayList<Object>();
                for (int i = 0; i < values.length; i++) {
                    String val = values[i].trim();
                    if (val.length() > 0) {
                    	if(flag){
                    		Date date = parseDate(val);
                            vals.add(date == null ? val : date);
                    	}else{
                    		vals.add(val);
                    	}
                    } else if (emptiable || isDate) {
                        vals.add("");
                    }
                }
                if (vals.size() > 0) {
                    params.put(key, vals);
                }
            }
        }
        return params;
    }
    public static <T> T applyUpdate(T newObj, T oldObj) throws ServletException {
        try {
            BeanInfo info = Introspector.getBeanInfo(newObj.getClass());
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                Method read = descriptor.getReadMethod();
                if (read != null && read.invoke(newObj) == null) {
                    Method write = descriptor.getWriteMethod();
                    if (write != null) {
                        write.invoke(newObj, read.invoke(oldObj));
                    }
                }
            }

            return newObj;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
