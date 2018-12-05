package com.pbtd.manager.component;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
//监听tomcat启动 关闭生命周期
@WebListener
public class ContextDestroyListener  implements ServletContextListener {  
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ContextDestroyListener.class);

    public static final List<String> MANUAL_DESTROY_THREAD_IDENTIFIERS = (List<String>) Arrays.asList("QuartzScheduler", "scheduler_Worker");  
    
    @Override  
    public void contextInitialized(ServletContextEvent sce) {  
    	 System.out.println("contextInitialized初始化");
    }  
 // 实现其中的销毁函数
    @Override  
    public void contextDestroyed(ServletContextEvent sce) {  
    	System.out.println("contextDestroyed初始化");
        destroyJDBCDrivers();  
        destroySpecifyThreads();  
    }  
  
    private void destroySpecifyThreads() {  
    	System.out.println("destroySpecifyThreads初始化");
        final Set<Thread> threads = Thread.getAllStackTraces().keySet();  
        for (Thread thread : threads) {  
            if (needManualDestroy(thread)) {  
                synchronized (this) {  
                    try {  
                        thread.stop();
                        logger.debug(String.format("Destroy  %s successful", thread));  
                    } catch (Exception e) {  
                        logger.warn(String.format("Destroy %s error", thread), e);  
                    }  
                }  
            }  
        }  
    }  
  
    private boolean needManualDestroy(Thread thread) {
    	System.out.println("needManualDestroy初始化");
        final String threadName = thread.getName();  
        for (String manualDestroyThreadIdentifier : MANUAL_DESTROY_THREAD_IDENTIFIERS) {  
            if (threadName.contains(manualDestroyThreadIdentifier)) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
    private void destroyJDBCDrivers() {  
    	System.out.println("destroyJDBCDrivers初始化");
        final Enumeration<Driver> drivers = DriverManager.getDrivers();  
        Driver driver;  
        while (drivers.hasMoreElements()) {  
            driver = drivers.nextElement();  
            try {  
                DriverManager.deregisterDriver(driver);  
                logger.debug(String.format("Deregister JDBC driver %s successful", driver));  
            } catch (SQLException e) {  
                logger.warn(String.format("Deregister JDBC driver %s error", driver), e);  
            }  
        }  
    }  
}  
