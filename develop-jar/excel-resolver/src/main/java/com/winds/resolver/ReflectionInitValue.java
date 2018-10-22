package com.winds.resolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;

import com.winds.common.constants.Common;
import com.winds.common.exceptions.LRUCacheException;
import com.winds.common.exceptions.ResolveFileException;
import com.winds.utils.DateUtil;
import com.winds.utils.MethodsLRUCacheUtil;

/**
 * Creater: cnblogs-WindsJune
 * Date: 2018/9/21
 * Time: 9:54
 * Description: No Description
 */
public class ReflectionInitValue {

    private int threadHashCodeKey=0;

    public void setThreadHashCodeKey(int threadHashCodeKey) {
        this.threadHashCodeKey = threadHashCodeKey;
    }

    public ReflectionInitValue(){
        this.setThreadHashCodeKey(Thread.currentThread().toString().hashCode());
    }

    /**
     * 通过反射动态将Excel读取的信息设置到对应的bean中
     *
     * @param object-存储对象bean
     * @param key-属性参数名
     * @param value-属性值
     * @throws Exception
     */
    public void setValue(Object object, String key, String value) throws LRUCacheException {
        String methodName = null;
        String paramType = null;
        Method[] methods = null;
        if (MethodsLRUCacheUtil.get(threadHashCodeKey) == null) {
            Class<?> clazz = object.getClass();
            methods = clazz.getDeclaredMethods();
            MethodsLRUCacheUtil.set(threadHashCodeKey, methods);
        } else {
            methods = MethodsLRUCacheUtil.get(threadHashCodeKey);
        }
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.startsWith("set") && methodName.toLowerCase().equals("set" + key.toLowerCase())) {
                Type[] types = method.getGenericParameterTypes();
                for (Type type : types) {
                    paramType = type.toString();
                    // 根据参数类型转化value，并进行set操作
                    excuteInvokeSetvalue(object, method, paramType, value, 0);
                }
                // 该属性已经执行setValue操作，无需循环
                break;
            }
        }
    }

    /**
     * 初始化对象bean
     *
     * @param object
     * @throws Exception
     */
    public void initBeans(Object object) throws ResolveFileException, LRUCacheException {
        int threadHashCodeKey=Thread.currentThread().toString().hashCode();
        String methodName = null;
        String paramType = null;
        Method[] methods = MethodsLRUCacheUtil.get(threadHashCodeKey);
        try {
            for (Method method : methods) {
                methodName = method.getName();
                if (methodName.startsWith("set")) {
                    Type[] types = method.getGenericParameterTypes();
                    for (Type type : types) {
                        paramType = type.getClass().getName();
                    }
                    // 根据参数类型转化value，并进行set初始化属性值
                    excuteInvokeSetvalue(object, method, paramType, "", 1);
                }
            }
        } catch (Exception e) {
            throw new ResolveFileException("初始化bean错误！Method:[ " + methodName + " ]");
        }
    }

    /**
     * 根据参数类型转化value，并进行set操作
     *
     * @param object-存储对象bean
     * @param method-执行的set对应属性的方法
     * @param paramType-属性参数类型
     * @param value-属性值
     * @param operationType-操作类型(0-设置属性，1-初始化bean)
     * @throws Exception
     */
    public void excuteInvokeSetvalue(Object object, Method method, String paramType, String value,
                                             int operationType){
        try {
            switch (paramType) {
                case Common.DATA_TYPE_long: {// 参数属性long
                    if (value !=null && value.contains(".")){
                        value=value.substring(0,value.lastIndexOf("."));
                    }
                    Long temp = Long.valueOf(operationType == 0 && value !=null ? value : "0");
                    method.invoke(object, temp);
                    break;
                }
                case Common.DATA_TYPE_boolean: {// 参数属性boolean
                    boolean temp = (operationType == 0 ? (Boolean.valueOf(value != null ? value:"false")) : false);
                    method.invoke(object, temp);
                    break;
                }
                case Common.DATA_TYPE_int: {// 参数属性int
                    if (value !=null && value.contains(".")){
                        value=value.substring(0,value.lastIndexOf("."));
                    }
                    int temp = Integer.valueOf(operationType == 0 && value!=null ? value : "0");
                    method.invoke(object, temp);
                    break;
                }
                case Common.DATA_TYPE_float: {// 参数属性float
                    if (value !=null && value.contains(".")){
                        value=value.substring(0,value.lastIndexOf("."));
                    }
                    float temp = Float.valueOf(operationType == 0 && value !=null ? value : "0");
                    method.invoke(object, temp);
                    break;
                }
                case Common.DATA_TYPE_double: {// 参数属性double
                    double temp = Double.valueOf(operationType == 0 && value !=null ? value : "0");
                    method.invoke(object, temp);
                    break;
                }
                case Common.DATA_TYPE_Long: {// 参数属性Long
                    if (value !=null && value.contains(".")){
                        value=value.substring(0,value.lastIndexOf("."));
                    }
                    Long temp = Long.valueOf(operationType == 0 && value!=null ? value : "0");
                    method.invoke(object, temp);
                    break;
                }
                case Common.DATA_TYPE_Integer: {// 参数属性Integer
                    if (value !=null && value.contains(".")){
                        value=value.substring(0,value.lastIndexOf("."));
                    }
                    int temp = Integer.valueOf(operationType == 0 && value!=null ? value : "0");
                    method.invoke(object, temp);
                    break;
                }
                case Common.DATA_TYPE_Date: {// 参数属性Date
                    if (value !=null && value.contains(".")){
                        value=value.substring(0,value.lastIndexOf("."));
                    }
                    Date dateTemp=DateUtil.strToDate(value);
                    method.invoke(object, operationType == 0 && dateTemp!=null ? dateTemp : null);
                    break;
                }
                default: {// 参数属性String
                    if (value !=null && value.contains(".")){
                        value=value.substring(0,value.lastIndexOf("."));
                    }
                    method.invoke(object, operationType == 0 ? value : null);
                    break;
                }
            }

        } catch ( IllegalAccessException e ) {
            throw new ResolveFileException("invoke方法错误！[Method:" + method.getName() + " [value:" + value + " ]");
        } catch ( InvocationTargetException e ) {
            throw new ResolveFileException("invoke方法错误！[Method:" + method.getName() + " [value:" + value + " ]");
        } catch (Exception e) {
            throw new ResolveFileException("字段属性错误！[Method:" + method.getName() + " [value:" + value + " ]");
        }


    }

    /**
     *
     * @param object
     * @return
     * @throws ResolveFileException
     */
    public Object invokeClone (Object object){
        try {
            Method[] methods=MethodsLRUCacheUtil.get((Thread.currentThread().toString().hashCode()));
            Object result=null;
            for (Method method:methods){
                if (method.getName().contains("clone")){
                    result=method.invoke(object);
                }
            }
            return result;

        }catch ( Exception e ){
            throw new ResolveFileException("解析Excel，反射执行set操作异常！");
        }

    }


}
