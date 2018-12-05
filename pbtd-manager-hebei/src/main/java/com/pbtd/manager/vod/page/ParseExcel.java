package com.pbtd.manager.vod.page;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ParseExcel {

	  public static List<Map<String, Object>> parseExcelalbumstatusupdate(InputStream inputSteam) throws IOException
	  {
	    List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    HSSFWorkbook workbook = new HSSFWorkbook(inputSteam);
	    HSSFSheet sheet = workbook.getSheetAt(0);

	    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	    	Map<String,Object> map = new HashMap<String,Object>();
	      HSSFRow row = sheet.getRow(i);

	      String showid = getCellValue(row.getCell(0));
	      if (!StringUtils.isNotEmpty(showid))
	        continue;
	      map.put("showid", showid);
	      map.put("status_next", getCellValue(row.getCell(1)));
	      map.put("paid_next", getCellValue(row.getCell(2)));
	      result.add(map);
	    }

	    return result;
	  }
	  
	  public static List<Map<String, Object>> parseExcelplaypolicy(InputStream inputSteam) throws IOException
	  {
	    List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    HSSFWorkbook workbook = new HSSFWorkbook(inputSteam);
	    HSSFSheet sheet = workbook.getSheetAt(0);

	    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	    	Map<String,Object> map = new HashMap<String,Object>();
	      HSSFRow row = sheet.getRow(i);

	      String mediaId = getCellValue(row.getCell(0));
	      if (!StringUtils.isNotEmpty(mediaId))
	        continue;
	      map.put("mediaId", mediaId);
	      map.put("play", getCellValue(row.getCell(1)));
	      map.put("type", getCellValue(row.getCell(2)));
	      map.put("showid", getCellValue(row.getCell(3)));
	      map.put("videoid", getCellValue(row.getCell(4)));
	      result.add(map);
	    }

	    return result;
	  }

	  private static String getCellValue(Cell cell)
	  {
	    String value = "";
	    if (cell == null) {
	      return value;
	    }
	    if (cell.getCellType() == 0)
	      value = Double.toString(cell.getNumericCellValue());
	    else if (cell.getCellType() == 1)
	      value = cell.getStringCellValue().trim();
	    else if (cell.getCellType() == 4)
	      value = Boolean.toString(cell.getBooleanCellValue());
	    else if (cell.getCellType() == 5)
	      value = Byte.toString(cell.getErrorCellValue());
	    else if (cell.getCellType() == 2)
	      value = cell.getCellFormula();
	    else {
	      value = cell.getStringCellValue();
	    }
	    return value;
	  }

}
