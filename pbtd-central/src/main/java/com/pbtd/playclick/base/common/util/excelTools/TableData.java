
package  com.pbtd.playclick.base.common.util.excelTools;

import java.util.LinkedList;
import java.util.List;


/**
 * 相关描述：表格数据格式
 *
 * @作者： wengzhonghui   
 * @创建时间：2012-11-29
 */
public class TableData {
	
	/*
	 * 报表标题高度 
	 *  
	 */
	public static short HEAD_TITLE_ROW_HEIGHT = 1000;
	
	/*
	 * 表头高度： 
	 *  
	 */
	public static short TITLE_ROW_HEIGHT = 750;
	
	/*
	 * 表各行高度 
	 *  
	 */
	public static short CONTENT_ROW_HEIGHT = 550;
	
	
	/**
	 * 字符串型
	 */
	public static final int STYLE_TYPE_STRING = 0;

	/**
	 * 浮点型，保留2位小数
	 */
	public static final int STYLE_TYPE_FLOAT_2 = 1;

	/**
	 * 浮点型，保留3位小数
	 */
	public static final int STYLE_TYPE_FLOAT_3 = 2;

	/**
	 * 整形
	 */
	public static final int STYLE_TYPE_INTEGER = 3;

	/**
	 * 红色背景
	 */
	public static final int STYLE_TYPE_RED_BG = 10;

	/**
	 * 黄色背景
	 */
	public static final int STYLE_TYPE_YELLOW_BG = 11;

	/**
	 * 绿色背景
	 */
	public static final int STYLE_TYPE_GREEN_BG = 12;

	private String sheetTitle;
	
	private TableHeaderMetaData header;

	private LinkedList<TableDataRow> rows;

	private int totalRows;
	
	public TableData(){}

	public TableData(TableHeaderMetaData header) {
		this.header = header;
		rows = new LinkedList<TableDataRow>();
	}

	public TableHeaderMetaData getTableHeader() {
		return header;
	}

	public void addRow(TableDataRow row) {
		rows.add(row);
	}

	public List<TableDataRow> getRows() {
		return rows;
	}

	public TableDataRow getRowAt(int index) {
		return rows.get(index);
	}

	public int getRowCount() {
		return rows.size();
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setHeader(TableHeaderMetaData header) {
		this.header = header;
	}

	public void setRows(LinkedList<TableDataRow> rows) {
		this.rows = rows;
	}

	public String getSheetTitle() {
		return sheetTitle;
	}

	public void setSheetTitle(String sheetTitle) {
		this.sheetTitle = sheetTitle;
	}

}
