
package  com.pbtd.manager.base.common.util.excelTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * 相关描述：转换excel并向前台输出数据
 *
 * @作者： wengzhonghui
 * @创建时间：2012-11-29
 */
public class JsGridReportBase {
	public SimpleDateFormat timeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static String MODULE_PATH = "excelDemo/module.xls";

	private HttpServletResponse response;

	private HttpSession session;

	private ServletOutputStream out;

	public JsGridReportBase() {
	}

	public JsGridReportBase(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.response = response;
		session = request.getSession();
		init(this.session);
	}

	private void init(HttpSession session) throws Exception {
		out = response.getOutputStream();
	}

	public void outDataToBrowser(TableData tableData) {
		StringBuffer outData = new StringBuffer();

		outData.append("{pageInfo: {totalRowNum: " + tableData.getTotalRows()
				+ "},");
		outData.append("data: [");
		boolean isFirst = true;

		TableHeaderMetaData headerMetaData = tableData.getTableHeader();
		List<TableDataRow> dataRows = tableData.getRows();
		try {
			for (TableDataRow dataRow : dataRows) {
				List<TableDataCell> dataCells = dataRow.getCells();
				int size = dataCells.size();
				if (!isFirst) {
					outData.append(",{");
					for (int i = 0; i < size; i++) {
						outData.append(headerMetaData.getColumnAt(i).getId()
								+ ": '" + dataCells.get(i).getValue() + "',");
					}
					int index = outData.lastIndexOf(",");
					outData.deleteCharAt(index);
					outData.append("}");
				} else {
					outData.append("{");
					for (int i = 0; i < size; i++) {
						outData.append(headerMetaData.getColumnAt(i).getId()
								+ ": '" + dataCells.get(i).getValue() + "',");
					}
					int index = outData.lastIndexOf(",");
					outData.deleteCharAt(index);
					outData.append("}");
					isFirst = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		outData.append("]");
		outData.append("}");

		try {
			out.print(outData.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param
	 * @return void
	 */
	private void stopGrouping(HSSFSheet sheet, HashMap<Integer, String> word,
			HashMap<Integer, Integer> counter, int i, int size, int rownum,
			HSSFCellStyle style) {
		String w = word.get(i);
		if (w != null) {
			int len = counter.get(i);
			CellRangeAddress address = new CellRangeAddress(rownum - len,
					rownum - 1, i, i);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, style);
			word.remove(i);
			counter.remove(i);
		}
		if (i + 1 < size) {
			stopGrouping(sheet, word, counter, i + 1, size, rownum, style);
		}
	}

	/**
	 *
	 * @param
	 * @return void
	 */
	private void generateColumn(HSSFSheet sheet, TableColumn tc, int maxlevel,
			int rownum, int colnum, HSSFCellStyle headerstyle) {
		HSSFRow row = sheet.getRow(rownum);
		if (row == null)
			row = sheet.createRow(rownum);

		HSSFCell cell = row.createCell(colnum);
		cell.setCellValue(tc.getDisplay());

		if (headerstyle != null)
			cell.setCellStyle(headerstyle);
		if (tc.isComplex()) {
			CellRangeAddress address = new CellRangeAddress(rownum, rownum,
					colnum, colnum + tc.getLength() - 1);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, headerstyle);

			int cn = colnum;
			for (int i = 0; i < tc.getChildren().size(); i++) {
				if (i != 0) {
					cn = cn + tc.getChildren().get(i - 1).getLength();
				}
				generateColumn(sheet, tc.getChildren().get(i), maxlevel,
						rownum + 1, cn, headerstyle);
			}
		} else {
			CellRangeAddress address = new CellRangeAddress(rownum, rownum
					+ maxlevel - tc.level, colnum, colnum);
			sheet.addMergedRegion(address);
			fillMergedRegion(sheet, address, headerstyle);
		}
		sheet.autoSizeColumn(colnum);

	}

	/**
	 *
	 * @param
	 * @return void
	 */
	private void fillMergedRegion(HSSFSheet sheet, CellRangeAddress address,
			HSSFCellStyle style) {
		for (int i = address.getFirstRow(); i <= address.getLastRow(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				row = sheet.createRow(i);
			for (int j = address.getFirstColumn(); j <= address.getLastColumn(); j++) {
				HSSFCell cell = row.getCell(j);
				if (cell == null) {
					cell = row.createCell(j);
					if (style != null)
						cell.setCellStyle(style);
				}
			}
		}
	}

	public HSSFWorkbook writeSheet(HSSFWorkbook wb, String title, HashMap<String, HSSFCellStyle> styles, String creator, TableData tableData) throws Exception {

		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		String create_time = formater.format(new Date());

		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDisplayGridlines(false);

		HSSFRow row = sheet.createRow(0);
//		HSSFCell cell = row.createCell(0);

		row.setHeight(TableData.TITLE_ROW_HEIGHT);


		TableHeaderMetaData headerMetaData = tableData.getTableHeader();
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData
//				.getColumnCount() - 1));



		int rownum = 0;

		HSSFCellStyle headerstyle = styles.get("TABLE_HEADER");

		int colnum = 0;
		for (int i = 0; i < headerMetaData.getOriginColumns().size(); i++) {
			TableColumn tc = headerMetaData.getOriginColumns().get(i);
			if (i != 0) {
				colnum += headerMetaData.getOriginColumns().get(i - 1)
						.getLength();
			}
			generateColumn(sheet, tc, headerMetaData.maxlevel, rownum, colnum,
					headerstyle);
		}
		rownum += headerMetaData.maxlevel;

		List<TableDataRow> dataRows = tableData.getRows();

		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		HashMap<Integer, String> word = new HashMap<Integer, String>();
		int index = 0;
		for (TableDataRow dataRow : dataRows) {
			row = sheet.createRow(rownum);
			row.setHeight(TableData.CONTENT_ROW_HEIGHT);

			List<TableDataCell> dataCells = dataRow.getCells();
			int size = headerMetaData.getColumns().size();
			index = -1;
			for (int i = 0; i < size; i++) {
				TableColumn tc = headerMetaData.getColumns().get(i);
				if (!tc.isVisible())
					continue;
				index++;

				String value = dataCells.get(i).getValue();
				if (tc.isGrouped()) {
					String w = word.get(index);
					if (w == null) {
						word.put(index, value);
						counter.put(index, 1);
						createCell(row, tc, dataCells, i, index, styles);
					} else {
						if (w.equals(value)) {
							counter.put(index, counter.get(index) + 1);
						} else {
							stopGrouping(sheet, word, counter, index, size,
									rownum, styles.get("STRING"));

							word.put(index, value);
							counter.put(index, 1);
							createCell(row, tc, dataCells, i, index, styles);
						}
					}
				} else {
					createCell(row, tc, dataCells, i, index, styles);
				}
			}
			rownum++;
		}

		stopGrouping(sheet, word, counter, 0, index, rownum, styles
				.get("STRING"));
		for (int c = 0; c <= headerMetaData.getColumns().size(); c++) {

		}
		for(int c=0;c<1000;c++){
			sheet.autoSizeColumn((short)c, true);
		}
		sheet.setGridsPrinted(true);

		return wb;
	}

	public void exportToExcel(String title, String creator, TableData tableData)throws Exception {
		//title = new String(title.getBytes("iso-8859-1"),"utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
	//	System.out.println(System.getProperty("user.dir"));
		HashMap<String, HSSFCellStyle> styles = initStyles(wb);

		wb = writeSheet(wb,title,styles,creator,tableData);

		String sFileName = title + ".xls";
//		response.setHeader("Content-Disposition", "attachment;filename="+ new String(sFileName.getBytes("gb2312"), "iso8859-1"));
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(sFileName, "UTF-8"));
		response.setHeader("Connection", "open");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream())  ;
	}

	public void exportToExcel(String title, String creator, List<TableData> tableDataLst)
		throws Exception {
		//title = new String(title.getBytes("iso-8859-1"),"utf-8");
		HSSFWorkbook wb = new HSSFWorkbook();
		HashMap<String, HSSFCellStyle> styles = initStyles(wb);

		int i = 1;
		for(TableData tableData : tableDataLst){
			String sheetTitle = tableData.getSheetTitle();
			sheetTitle = sheetTitle==null||sheetTitle.equals("")?"sheet"+i:sheetTitle;
			wb = writeSheet(wb,tableData.getSheetTitle(),styles,creator,tableData);
			i++;
		}

		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());
	}

	private void createCell(HSSFRow row, TableColumn tc,
			List<TableDataCell> data, int i, int index,
			HashMap<String, HSSFCellStyle> styles) {
		TableDataCell dc = data.get(i);
		HSSFCell cell = row.createCell(index);
		switch (tc.getColumnType()) {
		case TableColumn.COLUMN_TYPE_INTEGER:
			cell.setCellValue(dc.getIntValue());
			HSSFCellStyle style = styles.get("INT");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("INT_C");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_FLOAT_2:
			cell.setCellValue(dc.getDoubleValue());
			style = styles.get("D2");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("D2_C");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_FLOAT_3:
			cell.setCellValue(dc.getDoubleValue());
			style = styles.get("D3");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("D3_C");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_RED_BG:
			cell.setCellValue(dc.getValue());
			style = styles.get("RED_BG");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_YELLOW_BG:
			cell.setCellValue(dc.getValue());
			style = styles.get("YELLOW_BG");
			if (style != null)
				cell.setCellStyle(style);
			break;
		case TableColumn.COLUMN_TYPE_GREEN_BG:
			cell.setCellValue(dc.getValue());
			style = styles.get("GREEN_BG");
			if (style != null)
				cell.setCellStyle(style);
			break;
		default:
			if (dc.getValue().equalsIgnoreCase("&nbsp;"))
				cell.setCellValue("");
			else
				cell.setCellValue(dc.getValue());
			style = styles.get("STRING");
			if (row.getRowNum() % 2 != 0)
				style = styles.get("STRING_C");
			if (style != null)
				cell.setCellStyle(style);
		}
	}

	private HashMap<String, HSSFCellStyle> initStyles(HSSFWorkbook wb) {
 		HashMap<String, HSSFCellStyle> ret = new HashMap<String, HSSFCellStyle>();
		try {
			POIFSFileSystem fs = new POIFSFileSystem(getClass().getClassLoader().getResourceAsStream(MODULE_PATH));

			HSSFWorkbook src = new HSSFWorkbook(fs);
			HSSFSheet sheet = src.getSheetAt(0);

			buildStyle(wb, src, sheet, 0, ret, "TITLE");
			buildStyle(wb, src, sheet, 1, ret, "SUB_TITLE");
			buildStyle(wb, src, sheet, 2, ret, "SUB_TITLE2");

			buildStyle(wb, src, sheet, 4, ret, "TABLE_HEADER");
			buildStyle(wb, src, sheet, 5, ret, "STRING");
			buildStyle(wb, src, sheet, 6, ret, "INT");
			buildStyle(wb, src, sheet, 7, ret, "D2");
			buildStyle(wb, src, sheet, 8, ret, "D3");

			buildStyle(wb, src, sheet, 10, ret, "STRING_C");
			buildStyle(wb, src, sheet, 11, ret, "INT_C");
			buildStyle(wb, src, sheet, 12, ret, "D2_C");
			buildStyle(wb, src, sheet, 13, ret, "D3_C");

			buildStyle(wb, src, sheet, 15, ret, "RED_BG");
			buildStyle(wb, src, sheet, 16, ret, "YELLOW_BG");
			buildStyle(wb, src, sheet, 17, ret, "GREEN_BG");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 *
	 * @param
	 */
	private void buildStyle(HSSFWorkbook wb, HSSFWorkbook src, HSSFSheet sheet,
			int index, HashMap<String, HSSFCellStyle> ret, String key) {
		HSSFRow row = sheet.getRow(index);
		HSSFCell cell = row.getCell(1);
		HSSFCellStyle nstyle = wb.createCellStyle();
		ExcelUtils.copyCellStyle(wb, nstyle, src, cell.getCellStyle());
		ret.put(key, nstyle);
	}

	protected String getUTF8String(String string) {
		if (string == null) {
			return null;
		} else {
			try {
				String str = new String(string.getBytes("ISO8859-1"), "UTF-8");
				return str;
			} catch (Exception e) {
				e.printStackTrace();
				return string;
			}
		}
	}

	protected String getGBKString(String string) {
		if (string == null) {
			return null;
		} else {
			try {
				String str = new String(string.getBytes("ISO8859-1"), "GBK");
				return str;
			} catch (Exception e) {
				e.printStackTrace();
				return string;
			}
		}
	}

	public String fieldRender(String value) {
		if (value == null) {
			return "&nbsp;";
		} else {
			return value;
		}
	}

}
