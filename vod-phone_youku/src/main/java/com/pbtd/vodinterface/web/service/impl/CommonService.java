package com.pbtd.vodinterface.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.web.mapper.CommonMapper;
import com.pbtd.vodinterface.web.service.face.ICommonService;
@Service
public class CommonService implements ICommonService {

	@Autowired
	private CommonMapper commonmapper;
	
	@Override
	public List<Map<String, Object>> getChannel(Map<String, Object> map) {
		
		return commonmapper.getChannel(map);
	}


	@Override
	public List<Map<String, Object>> getLabel(Map<String, Object> map) {
		
		return commonmapper.getLabel(map);
	}


	@Override
	public List<Map<String, Object>> findChannel(Map<String, Object> map) {
		return commonmapper.findChannel(map);
	}


	@Override
	public List<Map<String, Object>> getpgetspecial(Map<String, Object> map) {
		return commonmapper.getpgetspecial(map);
	}


	@Override
	public List<Map<String, Object>> getpgetspecialvideo(Map<String, Object> map) {
		return commonmapper.getpgetspecialvideo(map);
	}


	@Override
	public List<Map<String, Object>> recommandpic(Map<String, Object> map) {

		return commonmapper.recommandpic(map);
	}


	@Override
	public List<Map<String, Object>>  recommandpiclabel(Map<String, Object> map) {
		return commonmapper.recommandpiclabel(map);
	}


	@Override
	public List<Map<String, Object>> bottomnavigation(Map<String, Object> map) {
		return commonmapper.bottomnavigation(map);
	}
	@Override
	public Map<String, Object> getlogo(Map<String, Object> map) {
		return commonmapper.getlogo(map);
	}

	@Override
	public List<Map<String, Object>> textrecommendation(Map<String, Object> map) {
		return commonmapper.textrecommendation(map);
	}


	@Override
	public List<Map<String, Object>> slideshow(Map<String, Object> map) {
		return commonmapper.slideshow(map);
	}


	@Override
	public List<Map<String, Object>> startSlideshow(Map<String, Object> map) {
		return commonmapper.startSlideshow(map);
	}


	@Override
	public List<Map<String, Object>> pgetchannelforuser(Map<String, Object> map) {
		return commonmapper.pgetchannelforuser(map);
	}


	@Override
	public List<Map<String, Object>> pgetchannelnotforuser(Map<String, Object> map) {
		return commonmapper.pgetchannelnotforuser(map);
	}


	@Override
	public Map<String, Object> findUser(Map<String, Object> map) {
		return commonmapper.findUser(map);
	}


	@Override
	public int addchannelforuser(Map<String, Object> map) {
		return commonmapper.addchannelforuser(map);
	}


	@Override
	public int deletechannelforuser(Map<String, Object> map) {
		return commonmapper.deletechannelforuser(map);
	}


	@Override
	public List<Map<String, Object>> findbychannel(Map<String, Object> map) {
		return commonmapper.findbychannel(map);
	}


	@Override
	public List<Map<String, Object>> getlabeltype(Map<String, Object> map) {
		return commonmapper.getlabeltype(map);
	}

}
