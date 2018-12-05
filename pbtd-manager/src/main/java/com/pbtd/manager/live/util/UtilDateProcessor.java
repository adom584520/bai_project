package com.pbtd.manager.live.util;  
  
import java.text.SimpleDateFormat;  
import java.util.Date;  
  
import net.sf.json.JsonConfig;  
import net.sf.json.processors.JsonValueProcessor;  
  
/** 
 * 定义一个自己的时间适配处理器 
 * @author Administrator 
 * 
 */  
public class UtilDateProcessor implements JsonValueProcessor{  
  
    private String format = "yyyy-MM-dd hh:mm:ss";//自定义时间格式化的样式  
    public UtilDateProcessor() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
      
    public UtilDateProcessor(String format) {  
        this.format = format;  
    }  
  
    public Object processArrayValue(Object arg0, JsonConfig arg1) {  
        // TODO Auto-generated method stub  
        return arg0;  
    }  
    /** 
     * 处理对象的值 
     * str 这个参数是当前需要处理的属性名 
     */  
    public Object processObjectValue(String str, Object obj, JsonConfig arg2) {  
        // TODO Auto-generated method stub  
        String ret = "";  
        if(obj instanceof java.util.Date){  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            ret = sdf.format(((Date) obj).getTime());  
        }  
        return ret;  
    }  
  
}  