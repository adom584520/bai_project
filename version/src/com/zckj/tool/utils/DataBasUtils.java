/**
 * @author liuxiaomeng
 * @datetime 2015-8-3_下午2:18:09
 */
package com.zckj.tool.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author liuxiaomeng
 * @datetime 2015-8-3_下午2:18:09
 */
public class DataBasUtils {
	/** 数据库URL */
	public static String url = ConstUtils.url + "?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true";

	public Connection conn = null;

	private static DataBasUtils flodCompress = new DataBasUtils();

	public static DataBasUtils getInstance() {
		if (DataBasUtils.flodCompress == null) {
			DataBasUtils.flodCompress = new DataBasUtils();
		}
		return DataBasUtils.flodCompress;
	}

	public DataBasUtils() {
	}

	/**
	 * 数据库连接建立
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-7_下午2:44:33
	 * @param sql
	 * @return
	 */
	public PreparedStatement getPrepareStatement(final String sql) {
		try {
			Class.forName(ConstUtils.name);// 指定连接类型
			this.conn = DriverManager.getConnection(DataBasUtils.url, ConstUtils.user, ConstUtils.password);// 获取连接
			return this.conn.prepareStatement(sql);// 准备执行语句
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 数据库连接关闭
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-7_下午2:44:45
	 */
	public void close() {
		try {
			this.conn.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
}
