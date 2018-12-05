package com.pbtd.playclick.yinhe.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.yinhe.domain.YhLabel;
import com.pbtd.playclick.yinhe.mapper.YhLabelMapper;
import com.pbtd.playclick.yinhe.service.IYhLabelService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class YhLabelService implements IYhLabelService {

	public static Logger log = Logger.getLogger(YhLabelService.class);

	@Autowired
	private YhLabelMapper yhLabelMapper;

	@Override
	public void addLabel(String label, String cnhId) {

		if (!"[]".equals(label)) {
			JSONArray result = JSONArray.fromObject(label);
			// 地区和标签
			for (int i = 0; i < result.size(); i++) {
				JSONObject jsonTemp = result.getJSONObject(i);
				if ((jsonTemp.get("typeId").toString().equals("1") && !"".equals(jsonTemp.get("typeId").toString()))
						|| (jsonTemp.get("typeId").toString().equals("2")
								&& !"".equals(jsonTemp.get("typeId").toString()))
						|| (jsonTemp.get("typeId").toString().equals("3")
								&& !"".equals(jsonTemp.get("typeId").toString()))
						|| (jsonTemp.get("typeId").toString().equals("6")
								&& !"".equals(jsonTemp.get("typeId").toString()))) {
					YhLabel yhLabel = new YhLabel();
					YhLabel labelz = yhLabelMapper.selectByPrimaryKey(jsonTemp.get("tagId").toString(), cnhId);
					if (labelz == null) {
						yhLabel.setTagId(jsonTemp.get("tagId").toString());
						yhLabel.setTagName(jsonTemp.get("tagName").toString());
						yhLabel.setTypeId(jsonTemp.get("typeId").toString());
						yhLabel.setTypeName(jsonTemp.get("typeName").toString());
						yhLabel.setChnId(cnhId);
						try {
							yhLabelMapper.insert(yhLabel);
						} catch (Exception e) {
							log.error("标签重复添加..." + jsonTemp.get("tagId").toString() + "::" + cnhId);
							continue;
						}
					}
				}

			}
		}
	}

	@Override
	public int insert(YhLabel label) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public YhLabel select(String cnhId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(YhLabel label) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletes(int ids) {
		// TODO Auto-generated method stub
		return 0;
	}

}
