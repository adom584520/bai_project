package gongdan.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParseExcel {

	/**
	 * @描述：读取数据
	 * @参数：@param Workbook
	 * @参数：@return
	 * @返回值：List<List<String>>
	 */
	public static List<Map<String, String>> getMapList(File file,int flag) throws IOException{
		List<Map<String,String>> dataLst = new ArrayList<Map<String,String>>();
		InputStream inputSteam = null;
		inputSteam = new FileInputStream(file.getPath());

		/** 判断文件的类型，是2003还是2007 */
		Workbook workbook = null;
		boolean isExcel2003 = true;
		if (WDWUtil.isExcel2007(file.getPath())){
			isExcel2003 = false;
		}
		try {
			if (isExcel2003){
				workbook = new HSSFWorkbook(inputSteam);
			}else{
				workbook = new XSSFWorkbook(inputSteam);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		switch (flag) {
		case 1:
			dataLst = parseExcelplaypolicy1(workbook);
			break;
		case 2:
			dataLst = parseExcelplaypolicy2(workbook);
			break;
		case 3:
			dataLst = parseExcelplaypolicy3(workbook);
			break;
		case 4:
			dataLst = parseExcelplaypolicy4(workbook);
			break;
		case 5:
			dataLst = parseExcelplaypolicy5(workbook);
			break;
		default:
			System.out.println("~~~~~~~~~~~~~~~~~~~文件名有误~~~~~~~~~~~~~~~~~");
			break;
		}
		return dataLst;
	}


	//1解析  tv电影progrem
	public static List<Map<String, String>> parseExcelplaypolicy1(Workbook workbook) throws IOException{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println("数据长度:"+sheet.getLastRowNum());

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			Row row = sheet.getRow(i);
			if (row == null){
				continue;
			}
			String  Code =  getCellValue(row.getCell(0));	//唯一标识  *Code
			String  ContentProvider =  getCellValue(row.getCell(1));	//CP源简拼
			String  LicensingWindowStart =  getCellValue(row.getCell(2));	//片源有效开始时间
			String  LicensingWindowEnd =  getCellValue(row.getCell(3));	//片源有效结束时间
			String  Name =  getCellValue(row.getCell(4));	//节目名称 *Name
			String  typeId =  getCellValue(row.getCell(5));	//频道ID typeId
			String  SeriesFlag =  getCellValue(row.getCell(6));	//剧集类型标识(多剧集子集填写1) SeriesFlag
			String  VolumnCount =  getCellValue(row.getCell(7));	//总集数 VolumnCount
			String  Director =  getCellValue(row.getCell(8));	//导演 Director
			String  SearchName =  getCellValue(row.getCell(9));	//搜索名称(简拼) SearchName
			String  ActorDisplay =  getCellValue(row.getCell(10));	//演员列表 ActorDisplay
			String  OriginalCountry =  getCellValue(row.getCell(11));	//国家地区 OriginalCountry
			String  ReleaseYear =  getCellValue(row.getCell(12));	//上映年份 ReleaseYear
			String  Description =  getCellValue(row.getCell(13));	//节目简介(长度不超过4000位) Description
			String  MovieType =  getCellValue(row.getCell(14));	//媒体类型(1-正片,2-预览片) MovieType
			String  MovieFileURL =  getCellValue(row.getCell(15));	//媒体文件URL MovieFileURL
			String  AudioType =  getCellValue(row.getCell(16));	//声道:0-其他 AudioType
			String  ScreenFormat =  getCellValue(row.getCell(17));	//画面比例:0-4x3 1-16x9(Wide) ScreenFormat
			String  ClosedCaptioning =  getCellValue(row.getCell(18));	//字幕标志:0-无字幕 1-有字幕   ClosedCaptioning
			String  PictureFileURL1 =  getCellValue(row.getCell(19));	//图片海报 PictureFileURL1
			String  PictureFileURL2 =  getCellValue(row.getCell(20));	//图片剧照 PictureFileURL2
			String  Type =  getCellValue(row.getCell(21));	//频道名称
			String  Duration =  getCellValue(row.getCell(22));	//时长(分钟) typeId
			if (!StringUtils.isNotEmpty(Code))
				continue;

			map.put("Code",Code);
			map.put("ContentProvider",ContentProvider);
			map.put("LicensingWindowStart",LicensingWindowStart);
			map.put("LicensingWindowEnd",LicensingWindowEnd);
			map.put("Name",Name);
			map.put("typeId",typeId);
			map.put("SeriesFlag",SeriesFlag);
			map.put("VolumnCount",VolumnCount);
			map.put("Director",Director);
			map.put("SearchName",SearchName);
			map.put("ActorDisplay",ActorDisplay);
			map.put("OriginalCountry",OriginalCountry);
			map.put("ReleaseYear",ReleaseYear);
			map.put("Description",Description);
			map.put("MovieType",MovieType);
			map.put("MovieFileURL",MovieFileURL);
			map.put("AudioType",AudioType);
			map.put("ScreenFormat",ScreenFormat);
			map.put("ClosedCaptioning",ClosedCaptioning);
			map.put("PictureFileURL1",PictureFileURL1);
			map.put("PictureFileURL2",PictureFileURL2);
			map.put("Type",Type);
			map.put("Duration",Duration);
			result.add(map);
		}
		return result;
	}
	//2 解析 tv 电视剧Program
	public static List<Map<String, String>> parseExcelplaypolicy2(Workbook workbook) throws IOException{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println("数据长度:"+sheet.getLastRowNum());

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			Row row = sheet.getRow(i);

			String	parentCode=  getCellValue(row.getCell(0));		//	全局唯一标识 parentCode
			String	Code=  getCellValue(row.getCell(1));		//	唯一标识 Code
			String	ContentProvider=  getCellValue(row.getCell(2));		//	CP源简拼
			String	LicensingWindowStart=  getCellValue(row.getCell(3));		//	片源有效开始时间
			String	LicensingWindowEnd=  getCellValue(row.getCell(4));		//	片源有效结束时间
			String	Name=  getCellValue(row.getCell(5));		//	节目名称 name
			String	SeriesFlag=  getCellValue(row.getCell(6));		//	剧集类型标识(多剧集子集填写1) SeriesFlag
			String	ProgramName=  getCellValue(row.getCell(7));		//	子集名称ProgramName
			String	SearchName=  getCellValue(row.getCell(8));		//	搜索名称(简拼)
			String	MovieType=  getCellValue(row.getCell(9));		//	媒体类型(1-正片,2-预览片)MovieType
			String	MovieFileURL=  getCellValue(row.getCell(10));		//	媒体文件URL MovieFileURL
			String	AudioType=  getCellValue(row.getCell(11));		//	声道:0-其他 AudioType
			String	ScreenFormat=  getCellValue(row.getCell(12));		//	画面比例:0-4x3 1-16x9(Wide) ScreenFormat
			String	ClosedCaptioning=  getCellValue(row.getCell(13));		//	字幕标志:0-无字幕 1-有字幕 ClosedCaptioning
			String	Sequence=  getCellValue(row.getCell(14));		//	排序号 Sequence
			String	Type=  getCellValue(row.getCell(15));		//	频道名称
			String	Duration=  getCellValue(row.getCell(16));		//	时长(分钟) Duration

			if (!StringUtils.isNotEmpty(Code))
				continue;

			map.put("parentCode",parentCode);
			map.put("Code",Code);
			map.put("ContentProvider",ContentProvider);
			map.put("LicensingWindowStart",LicensingWindowStart);
			map.put("LicensingWindowEnd",LicensingWindowEnd);
			map.put("Name",Name);
			map.put("SeriesFlag",SeriesFlag);
			map.put("ProgramName",ProgramName);
			map.put("SearchName",SearchName);
			map.put("MovieType",MovieType);
			map.put("MovieFileURL",MovieFileURL);
			map.put("AudioType",AudioType);
			map.put("ScreenFormat",ScreenFormat);
			map.put("ClosedCaptioning",ClosedCaptioning);
			map.put("Sequence",Sequence);
			map.put("Type",Type);
			map.put("Duration",Duration);
			result.add(map);
		}
		return result;
	}

	//3  解析 tv 电视剧Series(TV端)
	public static List<Map<String, String>> parseExcelplaypolicy3(Workbook workbook) throws IOException{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println("数据长度:"+sheet.getLastRowNum());

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			Row row = sheet.getRow(i);

			String	parentCode=  getCellValue(row.getCell(0));		//	全局唯一标识 parentCode
			String	ContentProvider=  getCellValue(row.getCell(1));		//	CP源简拼
			String	LicensingWindowStart=  getCellValue(row.getCell(2));		//	片源有效开始时间
			String	LicensingWindowEnd=  getCellValue(row.getCell(3));		//	片源有效结束时间
			String	Name=  getCellValue(row.getCell(4));		//	节目名称 name
			String	VolumnCount=  getCellValue(row.getCell(5));		//	总集数 volumnCount
			String	typeId=  getCellValue(row.getCell(6));		//	频道ID typeId
			String	Director=  getCellValue(row.getCell(7));		//	导演 Director
			String	SearchName=  getCellValue(row.getCell(8));		//	搜索名称(简拼) SearchName
			String	ActorDisplay=  getCellValue(row.getCell(9));		//	演员列表 ActorDisplay
			String	OriginalCountry=  getCellValue(row.getCell(10));		//	国家地区 OriginalCountry
			String	OrgAirDate=  getCellValue(row.getCell(11));		//	首播时间 OrgAirDate
			String	Description=  getCellValue(row.getCell(12));		//	节目简介(长度不超过4000位) Description
			String	PictureFileURL1=  getCellValue(row.getCell(13));		//	图片海报 PfileURL1
			String	PictureFileURL2=  getCellValue(row.getCell(14));		//	图片剧照 PfileURL2
			String	Type=  getCellValue(row.getCell(15));		//	频道名称

			if (!StringUtils.isNotEmpty(parentCode))
				continue;

			map.put("parentCode",parentCode);
			map.put("ContentProvider",ContentProvider);
			map.put("LicensingWindowStart",LicensingWindowStart);
			map.put("LicensingWindowEnd",LicensingWindowEnd);
			map.put("Name",Name);
			map.put("VolumnCount",VolumnCount);
			map.put("typeId",typeId);
			map.put("Director",Director);
			map.put("SearchName",SearchName);
			map.put("ActorDisplay",ActorDisplay);
			map.put("OriginalCountry",OriginalCountry);
			map.put("OrgAirDate",OrgAirDate);
			map.put("Description",Description);
			map.put("PictureFileURL1",PictureFileURL1);
			map.put("PictureFileURL2",PictureFileURL2);
			map.put("Type",Type);
			result.add(map);
		}
		return result;
	}

	//4 手机 电影
	public static List<Map<String, String>> parseExcelplaypolicy4(Workbook workbook) throws IOException{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println("数据长度:"+sheet.getLastRowNum());

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			Row row = sheet.getRow(i);

			String	Code=  getCellValue(row.getCell(0));		//	唯一标识
			String	LicensingWindowStart=  getCellValue(row.getCell(1));		//	片源有效开始时间
			String	LicensingWindowEnd=  getCellValue(row.getCell(2));		//	片源有效结束时间
			String	Name=  getCellValue(row.getCell(3));		//	节目名称
			String	SeriesFlag=  getCellValue(row.getCell(4));		//	剧集类型标识(多剧集子集填写1)
			String	typeId=  getCellValue(row.getCell(5));		//	频道ID
			String	Director=  getCellValue(row.getCell(6));		//	导演
			String	SearchName=  getCellValue(row.getCell(7));		//	搜索名称(简拼)
			String	ActorDisplay=  getCellValue(row.getCell(8));		//	演员列表
			String	OriginalCountry=  getCellValue(row.getCell(9));		//	国家地区
			String	ReleaseYear=  getCellValue(row.getCell(10));		//	上映年份
			String	OrgAirDate=  getCellValue(row.getCell(11));		//	首播时间
			String	Description=  getCellValue(row.getCell(12));		//	节目简介(长度不超过4000位)
			String	MovieType=  getCellValue(row.getCell(13));		//	媒体类型(1-正片,2-预览片)
			String	MovieFileURL=  getCellValue(row.getCell(14));		//	媒体文件URL
			String	BitRateType=  getCellValue(row.getCell(15));		//	声道:0-其他
			String	Resolution=  getCellValue(row.getCell(16));		//	画面比例:0-4x3 1-16x9(Wide)
			String	PictureFileURL1=  getCellValue(row.getCell(17));		//	海报图片
			String	PictureFileURL2=  getCellValue(row.getCell(18));		//	剧照图片
			String	PictureFileURL3=  getCellValue(row.getCell(19));		//	广告图片
			String	Type=  getCellValue(row.getCell(20));		//	频道名称
			String	Duration=  getCellValue(row.getCell(21));		//	时长(分钟)

			if (!StringUtils.isNotEmpty(Code))
				continue;

			map.put("Code",Code);
			map.put("LicensingWindowStart",LicensingWindowStart);
			map.put("LicensingWindowEnd",LicensingWindowEnd);
			map.put("Name",Name);
			map.put("SeriesFlag",SeriesFlag);
			map.put("typeId",typeId);
			map.put("Director",Director);
			map.put("SearchName",SearchName);
			map.put("ActorDisplay",ActorDisplay);
			map.put("OriginalCountry",OriginalCountry);
			map.put("ReleaseYear",ReleaseYear);
			map.put("OrgAirDate",OrgAirDate);
			map.put("Description",Description);
			map.put("MovieType",MovieType);
			map.put("MovieFileURL",MovieFileURL);
			map.put("BitRateType",BitRateType);
			map.put("Resolution",Resolution);
			map.put("PictureFileURL1",PictureFileURL1);
			map.put("PictureFileURL2",PictureFileURL2);
			map.put("PictureFileURL3",PictureFileURL3);
			map.put("Type",Type);
			map.put("Duration",Duration);
			result.add(map);
		}
		return result;
	}
	//5 手机电视剧
	public static List<Map<String, String>> parseExcelplaypolicy5(Workbook workbook) throws IOException{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println("数据长度:"+sheet.getLastRowNum());

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Map<String, String> map = new HashMap<String,String>();
			Row row = sheet.getRow(i);

			String	parentCode=  getCellValue(row.getCell(0));		//	全局唯一标识 1 2
			String	Code=  getCellValue(row.getCell(1));		//	唯一标识  2
			String	LicensingWindowStart=  getCellValue(row.getCell(2));		//	片源有效开始时间
			String	LicensingWindowEnd=  getCellValue(row.getCell(3));		//	片源有效结束时间
			String	Name=  getCellValue(row.getCell(4));		//	节目名称 1 2
			String	VolumnCount=  getCellValue(row.getCell(5));		//	总集数 1
			String	ProgramName=  getCellValue(row.getCell(6));		//	子集名称2
			String	Sequence=  getCellValue(row.getCell(7));		//	排序号 2
			String	SearchName=  getCellValue(row.getCell(8));		//	搜索名称(简拼)1
			String	Genre=  getCellValue(row.getCell(9));		//	内容流派信息
			String	Director=  getCellValue(row.getCell(10));		//	导演1
			String	ActorDisplay=  getCellValue(row.getCell(11));		//	演员列表1
			String	Language=  getCellValue(row.getCell(12));		//	语言
			String	OriginalCountry=  getCellValue(row.getCell(13));		//	国家地区1
			String	ReleaseYear=  getCellValue(row.getCell(14));		//	上映年份 1
			String	OrgAirDate=  getCellValue(row.getCell(15));		//	首播时间 1
			String	Description=  getCellValue(row.getCell(16));		//	节目简介(长度不超过4000位) 1
			String	PictureFileURL1=  getCellValue(row.getCell(17));		//	海报图片1
			String	PictureFileURL2=  getCellValue(row.getCell(18));		//	剧照图片1
			String	PictureFileURL3=  getCellValue(row.getCell(19));		//	广告图片1
			String	SeriesFlag=  getCellValue(row.getCell(20));		//	剧集类型标识(多剧集子集填写1) 2
			String	MovieType=  getCellValue(row.getCell(21));		//	媒体类型(1-正片,2-预览片) 2
			String	MovieFileURL=  getCellValue(row.getCell(22));		//	媒体文件URL2
			String	BitRateType=  getCellValue(row.getCell(23));		//	码流
			String	Resolution=  getCellValue(row.getCell(24));		//	分辨率类型
			String	Duration=  getCellValue(row.getCell(25));		//	时长(分钟) 2
			String	Type=  getCellValue(row.getCell(26));		//	频道名称
			String	typeId=  getCellValue(row.getCell(27));		//	频道ID

			if (!StringUtils.isNotEmpty(parentCode))
				continue;

			map.put("parentCode",parentCode);
			map.put("Code",Code);
			map.put("LicensingWindowStart",LicensingWindowStart);
			map.put("LicensingWindowEnd",LicensingWindowEnd);
			map.put("Name",Name);
			map.put("VolumnCount",VolumnCount);
			map.put("ProgramName",ProgramName);
			map.put("Sequence",Sequence);
			map.put("SearchName",SearchName);
			map.put("Genre",Genre);
			map.put("Director",Director);
			map.put("ActorDisplay",ActorDisplay);
			map.put("Language",Language);
			map.put("OriginalCountry",OriginalCountry);
			map.put("ReleaseYear",ReleaseYear);
			map.put("OrgAirDate",OrgAirDate);
			map.put("Description",Description);
			map.put("PictureFileURL1",PictureFileURL1);
			map.put("PictureFileURL2",PictureFileURL2);
			map.put("PictureFileURL3",PictureFileURL3);
			map.put("SeriesFlag",SeriesFlag);
			map.put("MovieType",MovieType);
			map.put("MovieFileURL",MovieFileURL);
			map.put("BitRateType",BitRateType);
			map.put("Resolution",Resolution);
			map.put("Duration",Duration);
			map.put("Type",Type);
			map.put("typeId",typeId);
			result.add(map);
		}
		return result;
	}



	private static String getCellValue(Cell cell)
	{
		String value = "";
		if (cell == null) {
			return value;
		}else{
			cell.setCellType(Cell.CELL_TYPE_STRING);
			value = cell.getStringCellValue().trim();
		}
		/*if (cell.getCellType() == 0)
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
		}*/
		return value;
	}

}
