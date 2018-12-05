
package com.pbtd.playclick.base.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 打印异常堆栈信息
 * 
 * @Title: ExceptionPrint
 * @Copyright: Copyright (c) 2010
 * @Company: BMS
 * @author: xujian
 * @version 1.0
 */
public class ExceptionPrint
{

    private static ExceptionPrint exceptionPrint = null;

    /**
     * 单例
     * @return ExceptionPrint
     */
    public static ExceptionPrint getInstance()
    {
        if (null == exceptionPrint)
        {
            exceptionPrint = new ExceptionPrint();
        }
        return exceptionPrint;
    }

    /**
     * 打印堆栈
     * 
     * @param t 参数
     * @return 堆栈信息
     */
    public String getStackTrace(Throwable t)
    {
        if (null == t)
        {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

}
