package com.pbtd.manager.vod.phone.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.common.domain.VodPaidSAlbuminfo;
import com.pbtd.manager.vod.phone.common.mapper.VodPaidAlbummapper;
import com.pbtd.manager.vod.phone.common.service.face.IVodPaidAlbumService;

@Service
public class VodPaidAlbumService implements IVodPaidAlbumService {

	
	@Autowired
	private  VodPaidAlbummapper VodPaidAlbummapper;
	
	
	@Override
	public int count(Map<String, Object> queryParams) {
		return VodPaidAlbummapper.count(queryParams);
	}

	@Override
	public List<Map<String, Object>> page(Map<String, Object> queryParams) {
		return VodPaidAlbummapper.page(queryParams);
	}

	@Override
	public VodPaidSAlbuminfo load(int id) {
		return VodPaidAlbummapper.load(id);
	}

	@Override
	public int insert(VodPaidSAlbuminfo m) {
		return VodPaidAlbummapper.insert(m);
	}

	@Override
	public int update(VodPaidSAlbuminfo m) {
		return VodPaidAlbummapper.update(m);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		return VodPaidAlbummapper.deletes(ids);
	}

}
