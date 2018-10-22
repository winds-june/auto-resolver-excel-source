package com.winds.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winds.common.constants.Common;
import com.winds.common.exceptions.ResolveFileException;
import com.winds.utils.StringUtil;

/**
 * @author :cnblogs-WindsJune
 * @version :1.1.0
 * @date :2018年9月20日 下午6:13:43
 * @comments :
 */

public class ExcelResolver<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelResolver.class);
	//存放属性集
	private  Map<Integer,String []> fieldsMap=new HashMap<>();
	//存放解析出Excel的数据
    private List<T> objectsList = new ArrayList<>();
    //反射运行时对象
    private Object object=null;
    //Excel文件路径
    private  String path =null;
    //反射初始化属性值对象
    private  ReflectionInitValue reflectionInitValue=null;
    //处理数字
    private static DecimalFormat df = new DecimalFormat("0");

    public List<T> getObjectsList() {
        return this.objectsList;
    }

    public ExcelResolver(Object object, String path) throws IOException {
        this.object=object;
        this.path=path;
		reflectionInitValue=new ReflectionInitValue();
        readExcel();
    }

    /**
     * 添加Object到List中
     * @param object
     * @return
     */
    public boolean addListObject(T object){
        boolean isSucceed=this.objectsList.add(object);
        return  isSucceed;
    }

	/**
	 * 读取excel,判断是xls结尾(2010之前)；还是xlsx结尾(2010以后)的Excel
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean readExcel() throws IOException {
		if (StringUtils.isEmpty(path)) {
			return false;
		} else {
			// 截取后缀名，判断是xls还是xlsx
			String postfix = path.substring(path.lastIndexOf(".") + 1);
			if (!StringUtils.isEmpty(postfix)) {
				if (Common.OFFICE_EXCEL_2003_POSTFIX_xls.equals(postfix)) {
					return readXls();
				} else if (Common.OFFICE_EXCEL_2010_POSTFIX_xlsx.equals(postfix)) {
					return readXlsx();
				}
			} else {
				LOGGER.error("文件后缀名有误！");
				throw new ResolveFileException("文件后缀名有误！" + "[" + path + "]");
			}
		}
		return false;
	}

	/**
	 * 读取xls(2010)之后的Excel
	 * 
	 * @return
	 * @throws IOException
	 */
	public  boolean readXlsx() throws IOException{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// 遍历sheet页
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			String [] fields=null;
			if (xssfSheet == null) {
				continue;
			}
			// 循环行
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				int cloumns=xssfRow.getLastCellNum();
				int i=0;
				//获取第一行的所有属性
				if (rowNum == 0){
				    fields=getFields(xssfRow,cloumns);
                    fieldsMap.put(numSheet,fields);
                    continue;
                }
                //遍历数据,反射set值
                while (i<cloumns){
                    XSSFCell field=xssfRow.getCell(i);
                    String value=getValue(field);
                    try {
						reflectionInitValue.setValue(object,fields[i],value);
                    }catch ( Exception e ){
                        throw new ResolveFileException(e.getMessage());
                    }
                    i++;
                }
                //通过反射执行clone复制对象
                Object result=reflectionInitValue.invokeClone(object);
				this.addListObject((T)result);
               // System.out.println(object.toString());
			}
		}
		return true;
	}

	/**
	 * 读取xls(2010)之前的Excel
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean readXls() throws IOException, ResolveFileException {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		// 遍历sheet页
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            String[] fields = null;
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                int cloumns=hssfRow.getLastCellNum();
                int i=0;
                //获取第一行的所有属性
                if (rowNum == 0){
                    //获取属性字段
                    fields=getFields(hssfRow,cloumns);
                    fieldsMap.put(numSheet,fields);
                    continue;
                }
                //遍历数据,反射set值
                while (i<cloumns){
                    HSSFCell field=hssfRow.getCell(i);
                    String value=getValue(field);
                    try {
                        //反射设置object属性值
						reflectionInitValue.setValue(object,fields[i],value);
                    }catch ( Exception e ){
                        throw  new ResolveFileException(e.getMessage());
                    }
                    i++;
                }
                //通过反射执行clone复制对象
                Object result=reflectionInitValue.invokeClone(object);
                this.addListObject((T)result);
			}
		}
		return true;
	}

	/**
	 * xlsx -根据数据类型，获取单元格的值
	 * @param xssfRow
	 * @return
	 */
	@SuppressWarnings({ "static-access" })
	private static String getValue(XSSFCell xssfRow) {
	    String value=null;
		try {
			if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
				// 返回布尔类型的值
				value=StringUtil.LRtrim(String.valueOf(xssfRow.getBooleanCellValue()));
			} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
				// 返回数值类型的值
				value=StringUtil.LRtrim(df.format(xssfRow.getNumericCellValue()));
			} else {
				// 返回字符串类型的值
				value=StringUtil.LRtrim(String.valueOf(xssfRow.getStringCellValue()));
			}
		} catch (Exception e) {
			//单元格为空，不处理
            value=null;
			LOGGER.error("单元格为空！");
		}
		return value;
	}

	/**
	 * xls-根据数据类型，获取单元格的值
	 * @param hssfCell
	 * @return
	 */
	@SuppressWarnings({ "static-access" })
	private static String getValue(HSSFCell hssfCell) {
	    String value=null;
		try {
			if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
				// 返回布尔类型的值
                value=StringUtil.LRtrim(String.valueOf(hssfCell.getBooleanCellValue()));
			} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
				// 返回数值类型的值
				value=StringUtil.LRtrim(df.format(hssfCell.getNumericCellValue()));
			} else {
				// 返回字符串类型的值
				value=StringUtil.LRtrim(String.valueOf(hssfCell.getStringCellValue()));
			}
		} catch (Exception e) {
			//单元格为空，不处理
            value=null;
			LOGGER.error("单元格为空！");
		}
		return value;
	}

    /**
     * xls Excel文件类型获取属性（2010之前）
     * @param cloumns
     * @return String[]
     */
	private  static String[] getFields (HSSFRow hssfRow,int cloumns){
	    String [] fields=new String[cloumns];
        int i=0;
        try {
            while (i<cloumns){
                HSSFCell field=hssfRow.getCell(i);
                String value=getValue(field);
                fields[i]=value.trim();
                i++;
            }
        }catch ( Exception e){
            throw  new ResolveFileException("获取属性集失败！");
        }
        return  fields;
    }

    /**
     * xlsx Excel文件类型获取属性（2010之后）
     * @param cloumns
     * @return String[]
     */
    private  static String[] getFields(XSSFRow xssfRow,int cloumns){
        String [] fields=new String[cloumns];
        int i=0;
        try {
            while (i<cloumns){
                XSSFCell field=xssfRow.getCell(i);
                String value=getValue(field);
                fields[i]=value.trim();
                i++;
            }
        }catch ( Exception e ){
            throw  new ResolveFileException("获取属性集失败！");
        }
        return  fields;
    }

}
