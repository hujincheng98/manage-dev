package com.skynet.rimp.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisService {
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	/**
	 * 保存方法（String类型操作） 
	 * @param key
	 * @param jsonObj
	 */
	public void setStrVal(final String key, final String value){
		if(redisTemplate.hasKey(key)){
			redisTemplate.delete(key);
		}
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**
	 * 获取数据（String类型操作） 
	 * @param key
	 * @return
	 */
	public String getStrVal(final String key){
		return redisTemplate.opsForValue().get(key).toString();
	}
	
	/**
	 * 删除操作
	 * @param key
	 */
	public void del(final String key){
		redisTemplate.delete(key);
	}
	
	/**
	 * 保存操作（Hash类型）
	 * @param key
	 * @param map
	 */
	public void setHashVal(final String key , final Map<String, String> map){
		if(redisTemplate.hasKey(key)){
			redisTemplate.delete(key);
		}
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	/**
	 * 获取操作（Hash类型）
	 * @return
	 */
	public List<String> getHashVal(final String key,final String... value ){
		
		return redisTemplate.execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<String> resultList = new ArrayList<String>();
				byte[] param = redisTemplate.getStringSerializer().serialize(key);
				//将String...转为byte[]...
				byte[][] valByte = new byte[value.length][];
				for (int i = 0; i < value.length; i++) {
					valByte[i] = redisTemplate.getStringSerializer().serialize(value[i]);
				}
				if(connection.exists(param)){
					List<byte[]> dataList = connection.hMGet(param, valByte);
					//将得到的结果集进行反序列化
					for (byte[] bt : dataList) {
						resultList.add(redisTemplate.getStringSerializer().deserialize(bt));
					}
				}
				return resultList;
			}
		});
	}
	
	
	/**
	 * 删除指定key下的Hash集合对于的key
	 * @param key
	 * @param hashKeys
	 */
	public void delHashValKey(final String key,final Object... hashKeys){
		redisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	/**
	 * 设置指定key下的hash值
	 * @param key 缓存key
	 * @param hashKey hash key
	 * @param value hash value
	 */
	public void setHashValKey(final String key,final String hashKey,final String value){
		redisTemplate.opsForHash().put(key, hashKey, value);
	}
	
	/**
	 * 清空当前DB的数据
	 */
	public void flushDB(){
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return null;
			}
		});
	}
	
	
}
