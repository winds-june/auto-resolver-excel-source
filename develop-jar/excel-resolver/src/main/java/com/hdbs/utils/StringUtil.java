package com.hdbs.utils;

/**
 * Creater: cnblogs-WindsJune
 * Date: 2018/10/16
 * Time: 16:07
 * Description: No Description
 */
public class StringUtil {

    /**
     * 去除字符串两端的所有空格
     * @param str
     * @return
     */
    public static String LRtrim(String str){
        StringBuffer sb=new StringBuffer();
        String[] strList=str.split("");
        int i=0,j=strList.length-1;
        // 去除字符串左边的所有空格
        while ( " ".equals(strList[i]) && i<strList.length ){
            if ( (i+1)<strList.length && " ".equals(strList[i+1]) ){
                strList[i]="";
            }else {
                break;
            }
            i++;
        }
        // 去除字符串右边的所有空格
        while ( " ".equals(strList[j]) && i>0 ){
            if ( (j-1)>0 && " ".equals(strList[j-1]) ){
                strList[j]="";
            }else {
                break;
            }
            i--;
        }
        for (String temp : strList){
            sb.append(temp);
        }
        return sb.toString().trim();
    }


}
