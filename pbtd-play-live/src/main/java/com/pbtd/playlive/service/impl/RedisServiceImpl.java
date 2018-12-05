package com.pbtd.playlive.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.service.IRedisService;
import com.pbtd.playlive.util.JSONUtil;
//import com.rd.ifaes.common.dict.ExpireTime;  
//import com.lh.common.util.JsonMapper;  
/** 
 *  
 * @author vic 
 * @desc resdis service 
 * 
 */  
@Service  
public class RedisServiceImpl implements IRedisService{  

	@Autowired  
	private  RedisTemplate<Serializable, Serializable> redisTemplate;  

	@Override  
	public boolean set(final String key, final String value) {  
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
			@Override  
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
				connection.set(serializer.serialize(key), serializer.serialize(value));  
				return true;  
			}  
		});  
		return result;  
	}  
	/** 
	 * 将map写入缓存 
	 * @param key 
	 * @param map 
	 * @param time 失效时间(秒) 
	 */  
//	public  <T> void set(String key,LiveCibnNextEpg value){  
//		 redisTemplate.opsForHash().put(key, String.valueOf(value.getVideoId()), value);
//	}  
	/** 
	 * 将map写入缓存 
	 * @param key 
	 * @param map 
	 * @param time 失效时间(秒) 
	 */  
	public  <T> void setMap(String key, Map<String, T> map){  
		redisTemplate.opsForHash().putAll(key, map);  
	}  

//	/** 
//	 * 获取map缓存 
//	 * @param key 
//	 * @param clazz 
//	 * @return 
//	 */  
//	public  <T> Map<String, T> mget(String key, Class<T> clazz){  
//		BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);   
//		return boundHashOperations.entries();  
//	}  
	
	
    /** 
     * 向key对应的map中添加缓存对象 
     * @param key   cache对象key 
     * @param field map对应的key 
     * @param value     值 
     */  
    public  void addMap(String key, String field, String value){  
    	redisTemplate.opsForHash().put(key, field, value);  
    }  

	/** 
	 * 获取map缓存 
	 * @param key 
	 * @param clazz 
	 * @return 
	 */  
	//    public static <T> T getMap(String key, Class<T> clazz){  
	//        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);   
	//        Map<String, String> map = boundHashOperations.entries();  
	//        return JsonMapper.parseObject(map, clazz);  
	//    }  

	/** 
	 * 获取map缓存中的某个对象 
	 * @param key 
	 * @param field 
	 * @param clazz 
	 * @return 
	 */  
	@SuppressWarnings("unchecked")
	public  <T> T getMapField(String key, String field){  
		return (T)redisTemplate.boundHashOps(key).get(field);  
	}  


	public String get(final String key){  
		String result = redisTemplate.execute(new RedisCallback<String>() { 
			@Override  
			public String doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
				byte[] value =  connection.get(serializer.serialize(key));  
				return serializer.deserialize(value);  
			}  
		});  
		return result;  
	}  

	@Override  
	public boolean expire(final String key, long expire) {  
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);  
	}  

	@Override  
	public <T> boolean setList(String key, List<T> list) {  
		String value = JSONUtil.toJson(list);  
		return set(key,value);  
	}  

	@Override  
	public <T> List<T> getList(String key,Class<T> clz) {  
		String json = get(key);  
		if(json!=null){  
			List<T> list = JSONUtil.toList(json, clz);  
			return list;  
		}  
		return null;  
	}  

	@Override  
	public long lpush(final String key, Object obj) {  
		final String value = JSONUtil.toJson(obj);  
		long result = redisTemplate.execute(new RedisCallback<Long>() {  
			@Override  
			public Long doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
				long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));  
				return count;  
			}  
		});  
		return result;  
	}  

	@Override  
	public long rpush(final String key, Object obj) {
		final String value = JSONUtil.toJson(obj);  
		long result = redisTemplate.execute(new RedisCallback<Long>() {  
			@Override  
			public Long doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
				long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));  
				return count;  
			}  
		});  
		return result;  
	}  

	@Override  
	public String lpop(final String key) {  
		String result = redisTemplate.execute(new RedisCallback<String>() {  
			@Override  
			public String doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
				byte[] res =  connection.lPop(serializer.serialize(key));  
				return serializer.deserialize(res);  
			}  
		});  
		return result;  
	}  

}  