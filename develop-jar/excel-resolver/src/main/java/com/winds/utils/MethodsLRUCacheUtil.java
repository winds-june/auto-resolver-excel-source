package com.winds.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winds.common.exceptions.LRUCacheException;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Creater: cnblogs-WindsJune
 * Date: 2018/9/20
 * Time: 19:44
 * Description: LinkedHashMap实现LRU缓存不同线程反射获取的Method方法
 */
public class MethodsLRUCacheUtil {
    private  static  final Logger LOGGER=LoggerFactory.getLogger(MethodsLRUCacheUtil.class);
    //缓存容量
    private static final int cacheSize = 10;

    private static final Map<Integer,Method[]> cacheMap = new LinkedHashMap<Integer, Method[]>((int) Math.ceil(cacheSize / 0.75f) + 1, 0.75f, true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer,Method[]> eldest){

            return  size()> cacheSize;
            
        }
    };

    /**
     * 设置缓存
     * @param key
     * @param methods
     * @return boolean
     */
    public static boolean set (Integer key,Method [] methods) throws LRUCacheException {
        try {
               LOGGER.info("线程编号[{}](hashCode)执行反射获取的Methods通过LRU缓存",key);
               cacheMap.put(key,methods);
               return true;
        }
        catch ( Exception e ){
              throw new LRUCacheException("Set LRU缓存异常！");
        }
    }

    /**
     * 获取缓存的Method
     * @param key
     * @return Method
     */
    public static Method[] get(Integer key) throws LRUCacheException {
        Method[] methods=null;
        try {
            methods=cacheMap.get(key);
        }catch ( Exception e ){
               throw new LRUCacheException("Get LRU缓存异常！{}");
        }
        return methods;
    }
}
