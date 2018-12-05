package com.pbtd.playclick.yinhe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.yinhe.domain.YhActors;
import com.pbtd.playclick.yinhe.mapper.YhactorsMapper;
import com.pbtd.playclick.yinhe.service.IYhactorsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class YhactorsService implements IYhactorsService {

	@Autowired
	private YhactorsMapper yhactorsMapper;

	@Override
	public void addActors(String actors) {
		String str = actors;
		if (!"[]".equals(str)) {
			JSONArray result = JSONArray.fromObject(str);
			// 演员和导演
			for (int i = 0; i < result.size(); i++) {
				JSONObject jsonTemp = result.getJSONObject(i);
				if (jsonTemp.get("type").toString().equals("1") && !"".equals(jsonTemp.get("code").toString())) {
					YhActors yhActors = new YhActors();
					YhActors director = yhactorsMapper.selectByPrimaryKey(jsonTemp.get("code").toString());
					if (director == null) {
						yhActors.setCode(jsonTemp.get("code").toString());
						yhActors.setName(jsonTemp.get("name").toString());
						yhActors.setDirector("1");
						yhactorsMapper.insert(yhActors);
					} else {
						if (director.getDirector() == null) {
							yhactorsMapper.updateDirector("1",jsonTemp.get("code").toString());
						}
					}
				} else if (jsonTemp.get("type").toString().equals("3") && !jsonTemp.get("code").toString().equals("")) {
					YhActors yhActors = new YhActors();
					YhActors actor = yhactorsMapper.selectByPrimaryKey(jsonTemp.get("code").toString());
					if (actor == null) {
						yhActors.setCode(jsonTemp.get("code").toString());
						yhActors.setName(jsonTemp.get("name").toString());
						yhActors.setActor("1");
						yhactorsMapper.insert(yhActors);
					} else {
						if (actor.getActor() == null) {
							yhactorsMapper.updateActor("1",jsonTemp.get("code").toString());
						}
					}
				}
			}
		}
	}

	@Override
	public int insert(YhActors ators) {

		return 0;
	}

	@Override
	public YhActors select(String code, String type) {

		return null;
	}

	@Override
	public int update(YhActors ators) {

		return 0;
	}

	@Override
	public int deletes(int ids) {

		return 0;
	}

}
