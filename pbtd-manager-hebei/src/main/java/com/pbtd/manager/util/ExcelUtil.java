package com.pbtd.manager.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {
	private ExcelUtil() {
	}

	/**
	 * excel支持的文件格式
	 */
	public static final String SUFFIX_NAME = ".xlsx";

	/**
	 * 校验excel文件名格式
	 * 
	 * @param excelName
	 * @return 成功：true，失败：false
	 */
	public static boolean validateExcelName(String excelName) {
		 if (excelName == null || !isExcel2007(excelName)){
	            return false;    
	        }  
		return true;
	}
	
    /**
     * 是否是2003的excel，返回true是2003
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath)  {
    	//正则表达式：用.分割，字母必须为xls，忽略大小写
        return filePath.matches("^.+\\.(?i)(xls)$");    
    }    
     
    /**
     * 是否是2007的excel，返回true是2007
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath)  {    
        return filePath.matches("^.+\\.(?i)(xlsx)$");    
    }
    
    public static <T> List<T> excelToList(MultipartFile mfile,List<T> data){
    	
		return data;
    }
    
    /**
	 * 验证上传的excel文件是否正确
	 * 
	 * @param file
	 * @return 正确：null,错误：返回对应字符串
	 */
	public static String validateExcelImport(String fileName, MultipartFile file) {
		// 判断文件是否为空
		if (file == null) {
			return "该文件不存在！";
		}
		// 验证文件名是否合格
//		if (validateExcelName(fileName)) {
//			return "文件格式错误！";
//		}
		// 进一步判断文件内容是否为空（即判断其大小是否为0）
		long size = file.getSize();
		if (size == 0) {
			return "文件内容不能为空！";
		}
		return null;
	}

}
