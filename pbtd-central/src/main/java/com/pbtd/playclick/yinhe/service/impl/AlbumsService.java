package com.pbtd.playclick.yinhe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.base.common.easyui.ComboBoxOptionModel;
import com.pbtd.playclick.yinhe.mapper.vodAlbumsMapper;
import com.pbtd.playclick.yinhe.domain.Albums;
import com.pbtd.playclick.yinhe.domain.AlbumsWithBLOBs;
import com.pbtd.playclick.yinhe.domain.Gitvvideo;
import com.pbtd.playclick.yinhe.service.IAlbumsService;
@Service
public class AlbumsService implements IAlbumsService {


    @Autowired
    private vodAlbumsMapper albumsMapper;
    
    
	@Override
	public int count(Map<String, Object> queryParams) {
	return 	albumsMapper.count(queryParams);
	}

	@Override
	public List<Albums> page(int start, int limit, Map<String, Object> queryParams) {
		
		return albumsMapper.page(start, limit, queryParams);
	}

	@Override
	public List<Albums> find(Map<String, Object> queryParams) {
		
		return albumsMapper.find(queryParams);
	}

	@Override
	public List<ComboBoxOptionModel> choosechannel(Map<String, Object> queryParams) {
		
		return albumsMapper.choosechannel(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return albumsMapper.generatePosition(queryParams);
	}

	@Override
	public AlbumsWithBLOBs load(String id) {
		return albumsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(Albums albums) {
		
		return albumsMapper.insert(albums);
	}

	@Override
	public int update(Albums albums) {
		
		return albumsMapper.update(albums);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return albumsMapper.deletes(ids);
	}

	@Override
	public int save(Map<String, Object> map) {
		
		return albumsMapper.save(map);
	}

	@Override
	public List<Gitvvideo> findGitvvideo(Map<String, Object> queryParams) {
		return albumsMapper.findGitvvideo(queryParams);
	}

}
