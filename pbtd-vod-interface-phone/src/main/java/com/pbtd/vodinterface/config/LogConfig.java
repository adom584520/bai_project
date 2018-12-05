package com.pbtd.vodinterface.config;


import java.util.Calendar;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogConfig {

    private static final Logger logger = LoggerFactory.getLogger(LogConfig.class);
    long beginTime = 0L;
    long endTime=0L;
    long executeTime=0L;
    
    long controllerBeginTime=0L;
    long controllerEndTime=0L;
    long controllerExeTime=0L;
    
    long ipBeginTime=0L;
    long ipEndTime=0L;
    long ipExeTime=0L;

    @Pointcut("execution(public * com.pbtd.vodinterface.web.controller.*.*(..))")
    public void exeMethod() {}

    // @Order(5)
    @Before("exeMethod()")
    public void doBefore(JoinPoint joinPoint) {
    	beginTime=Calendar.getInstance().getTimeInMillis();
    	// 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        try {
			StringBuilder builder=new StringBuilder();
			builder.append(" -----------------begin-------------------------");
			builder.append("\n请求IP：");
			
			this.ipBeginTime=Calendar.getInstance().getTimeInMillis();
			builder.append(this.getRequestIP(request));
			this.ipEndTime=Calendar.getInstance().getTimeInMillis();
			this.ipExeTime=this.ipEndTime-this.ipBeginTime;
			builder.append(" 获取IP时间："+this.ipExeTime+"ms");
			
			builder.append("\n请求路径：");
			String uri=request.getRequestURI();
			builder.append(uri);
			builder.append("  \n请求参数：");
			@SuppressWarnings("rawtypes")
			Enumeration em = request.getParameterNames();
			 while (em.hasMoreElements()) {
			    String name = (String) em.nextElement();
			    String value = request.getParameter(name);
			    builder.append(name+":"+value+" ");
			}
			logger.info(builder.toString());
            this.controllerBeginTime=Calendar.getInstance().getTimeInMillis();
            
            
		/*	logger.info(joinPoint.getSignature().getDeclaringType().toString());
            logger.info(joinPoint.getSignature().getDeclaringTypeName());
            logger.info(String.valueOf(joinPoint.getSignature().getModifiers()));
            logger.info(joinPoint.getSignature().getName());
        */
			
        } catch (Exception e) {
            logger.error("打印方法前日志时：", e);
        }
    }

    @AfterReturning(returning = "result", pointcut = "exeMethod()")
    public void doAfterReturning(JoinPoint joinPoint,Object result) {
    	this.controllerEndTime=Calendar.getInstance().getTimeInMillis();
    	this.controllerExeTime=this.controllerEndTime-this.controllerBeginTime;
    	ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request=requestAttributes.getRequest();
        try {
        	StringBuilder builder=new StringBuilder("\n请求路径：");
			String uri=request.getRequestURI();
			builder.append(uri);
			builder.append("  \n返回数据：");
			builder.append(result);
			builder.append("  \n接口执行耗时："+this.controllerExeTime+"ms");
			this.endTime=Calendar.getInstance().getTimeInMillis();
			this.executeTime=endTime-beginTime;
			builder.append("  \n全部执行耗时："+this.executeTime+"ms");
			builder.append(" \n-------------------end-----------------------\n");
			logger.info(builder.toString());
            
        } catch (Exception e) {
            logger.error("打印方法后日志时：", e);
        }
    }   
    
    
    private String getRequestIP(HttpServletRequest request){
    	String xffIp=request.getHeader("X-Forwarded-For");
    	String xrlIp=request.getHeader("X-Real-IP");
    	logger.info("xffIp:"+xffIp);
    	logger.info("    xffIp："+xffIp);
    	if(StringUtils.isNotEmpty(xffIp) && !"unKnown".equalsIgnoreCase(xffIp)){
    		String[] ips=xffIp.split(",");
    		if(ips.length>0){
    			return ips[0];
    		}
    	}
    	if(StringUtils.isNotEmpty(xrlIp) && !"unKnown".equalsIgnoreCase(xrlIp)){
    		return xrlIp;
    	}
    	return request.getRemoteAddr();
    }
    
    
    
}