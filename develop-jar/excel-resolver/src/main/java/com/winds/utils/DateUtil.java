package com.winds.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creater: cnblogs-WindsJune
 * Date: 2018/10/16
 * Time: 14:49
 * Description: No Description
 */
public class DateUtil {

    private static final String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";

    private static final String YYYY_MM_DD="yyyy-MM-dd";

    /**
     * 采用默认格式转为日期
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr){
        Date date=null;
        SimpleDateFormat format=null;

        if (dateStr !=null && dateStr.trim().length()>10){
            format=new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        }else {
            format=new SimpleDateFormat(YYYY_MM_DD);
        }
        try {
            date=format.parse(dateStr);
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 指定格式格式化转为日期
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date strToDate(String dateStr,String pattern){
        Date date=null;
        try {
            SimpleDateFormat format=new SimpleDateFormat(pattern);
            date=format.parse(dateStr);
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        return date;
    }

}
