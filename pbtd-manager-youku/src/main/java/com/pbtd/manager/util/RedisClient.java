package com.pbtd.manager.util;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.pbtd.manager.conf.RedisConfValue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:redis工具类
 * @ClassName:
 * @date 2016年10月31日 上午11:25:06
 */
public class RedisClient{
	
	private  static  String IP = RedisConfValue.IP; 
	private static  String PORT = RedisConfValue.PORT;         // 端口
	private static  String AUTH=RedisConfValue.AUTH;    // 密码(原始默认是没有密码)
	private static int   MAX_ACTIVE = 1024;       // 最大连接数
	private static int   MAX_IDLE = 200; 	    // 设置最大空闲数
	private static int   MAX_WAIT = 10000;        // 最大连接时间
	private static int   TIMEOUT = 10000;         // 超时时间
	private static boolean BORROW = true;         // 在borrow一个事例时是否提前进行validate操作
	private static JedisPool pool = null;
	private static Logger logger = Logger.getLogger(RedisClient.class);
	/**
	 * 初始化线程池
	 */
	static
	{
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(BORROW);
			
			if(AUTH== null || "".equals(AUTH) ||  " ".equals(AUTH)){ 
				pool = new JedisPool(config, IP, Integer.valueOf(PORT), TIMEOUT);
			}else{
				pool = new JedisPool(config, IP, Integer.valueOf(PORT), TIMEOUT, AUTH);
			}
		} catch (Exception e) {
			logger.error("相关推荐redis连接错误: "+e);
			
		}
	}
	/**
	 * 获取连接
	 */
	public static synchronized  Jedis getJedis()
	{
		try
		{
			if(pool != null)
			{	
				return pool.getResource();
			}
			else
			{
				return null;
			}
		}
		catch (Exception e) {
			logger.info("连接池连接异常");
			return null;
		}
	}
	/**
	 * @Description:设置失效时间
	 * @param @param key
	 * @param @param seconds
	 * @param @return 
	 * @return boolean 返回类型
	 */
	public static void disableTime(String key,int seconds)
	{
		Jedis jedis = null;
		try
		{
			jedis = getJedis();
			jedis.expire(key, seconds);

		}
		catch (Exception e) {
			logger.debug("设置失效失败.");
		}finally {
			getColse(jedis);
		}
	}


	/**
	 * @Description:存储key~value
	 * @param @param key
	 * @param @param value 
	 * @return void 返回类型
	 */

	public static boolean set(String key,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = getJedis();
			String code = jedis.set(key, value);
			if("ok".equals(code) || "OK".equals("code"))
			{
				return true;
			}
		}
		catch (Exception e) {
			logger.debug("插入数据有异常.");
			return false;
		}finally {
			getColse(jedis);
		}
		return false;
	}
	/**
	 * @Description:存储key~value
	 * @param @param key
	 * @param @param value 
	 * @return void 返回类型
	 */

	public static boolean set(String key,int date,String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = getJedis();
			String code = jedis.setex(key,date,value);
			if("ok".equals(code) || "OK".equals("code"))
			{
				return true;
			}
		}
		catch (Exception e) {
			logger.debug("插入数据有异常.");
			return false;	
		}finally {
			getColse(jedis);
		}
		return false;
	}


	/**
	 * 从缓存获取数据
	 * @param key 缓存KEY
	 * @return 缓存数据
	 */
	public static String get(String key) {
		if (key == null) {
			return null;
		}
		Jedis jedis = null;
		String data = null;
		try {
			jedis = getJedis();
			data =  jedis.get(key);
		} catch (Exception e) {
			return null;
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return data;
	}
	/**
	 * 从缓存获取数据
	 * @param key 缓存KEY
	 * @return 缓存数据
	 */
	public static boolean checktoken(String userid,String token) {
		if (userid == null || token == null) {
			return false;
		}
		Jedis jedis = null;
		String data = null;
		StringBuilder key = new StringBuilder();
		key.append(userid);
		key.append(token);
		try {
			jedis = getJedis();
			data =  jedis.get(key.toString());
		} catch (Exception e) {
			return false;
		}
		finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		if(data != null || "".equals(data)){
			return true;
		}
			return false;
	}
	/**
	 * @Description:删除key
	 * @param @param key
	 * @param @return 
	 * @return boolean 返回类型
	 */
	public static boolean delKey(String key)
	{
		Jedis jedis = null;
		try
		{
			jedis = getJedis();
			Long code = jedis.del(key);
			if(code > 1)
			{
				return true;
			}
		}
		catch (Exception e) {
			logger.debug("删除key异常.");
			return false;
		}finally {
			getColse(jedis);
		}
		return false;
	}


	/***
	 *删除 缓存 传入和哪张表相关的缓存
	 * @param key
	 * @return
	 */
	public static int del(String tablekey) {
		Jedis jedis = null; int num = 0;
		try {
			jedis = getJedis();
			Set<String> set = jedis.keys(tablekey+"*");  
			Iterator<String> it = set.iterator();  
			while(it.hasNext()){  
				String keyStr = it.next();  
				logger.info("删除的key："+keyStr);  
				jedis.del(keyStr);  
				num++;
			} 
		}catch (Exception e) {
			logger.debug("模糊删除key异常.");
		}finally {
			getColse(jedis);
		}
		return num;
	}
	/***
	 *清空 缓存 
	 * @param key
	 * @return
	 */
	public String flushdb() {
		Jedis jedis = null; 
		try {
			jedis = getJedis();
			jedis.flushDB();
		}catch (Exception e) {
			logger.debug("清空缓存异常.");
			return "no";
		}finally {
			getColse(jedis);
		}
		return "ok";
	}

	/**
	 * @Description: 关闭连接
	 * @param @param jedis 
	 * @return void 返回类型
	 */

	public static void getColse(Jedis jedis)
	{
		if(jedis != null)
		{
			jedis.close();
		}
	}

}