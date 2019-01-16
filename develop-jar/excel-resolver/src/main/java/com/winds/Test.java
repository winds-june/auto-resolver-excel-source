package com.winds;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winds.beans.Area;
import com.winds.beans.Student;
import com.winds.common.exceptions.ResolveFileException;
import com.winds.resolver.ExcelResolver;

/**
* @author   :汪世生/cnblogs：WindsJune
* @version  :1.1.0
* @date     :2018年9月20日 下午6:12:18
* @comments :主测试方法
*/

public class Test {

    //解析student
    private static final String studentFile="student.xlsx";
    private static final Student student=new Student();

    //解析area
    private static final String areaFile="area-init-data.xlsx";
    private static final Area area=new Area();

	public static void main(String[] args) {

        try {
            ExcelResolver<Student> resolver=new ExcelResolver(student,studentFile);
            List<Student> studentList=resolver.getObjectsList();
            System.out.println("得到的结果：");
            for(Student obj:studentList){
                Student studentInfo= obj;
                System.out.println(studentInfo.toString());
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( ResolveFileException e ) {
            e.printStackTrace();
        }

    }

}
