package com.pbtd.playclick.base.common.easyui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * 对应EasyUI datagrid组件Json模型
 *
 * @author huangdiwen
 */
public class DataGridRowModel extends HashMap<String, Object> {

	private static final long serialVersionUID = 8943924192950327701L;
	private boolean useDateFormat = true;
	private String dateFormat = null;

	public void useDateFormat(boolean useDateFormat) {
		this.useDateFormat = useDateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		this.useDateFormat = dateFormat != null;
	}

	public DataGridRowModel addAttribute(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public DataGridRowModel setObject(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (this.useDateFormat) {
				DateFormat dateFormat = new SimpleDateFormat(
						this.dateFormat != null ? this.dateFormat : "yyyy-MM-dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
			}
			String content = mapper.writeValueAsString(obj);
			Map<String, Object> map = mapper.readValue(content, new TypeReference<Map<String, Object>>() {
			});
			super.putAll(map);
			return this;
		} catch (IOException e) {
			throw new IllegalArgumentException("将对象转成" + this.getClass().getName() + "时候出现异常错误！", e);
		}
	}
}
