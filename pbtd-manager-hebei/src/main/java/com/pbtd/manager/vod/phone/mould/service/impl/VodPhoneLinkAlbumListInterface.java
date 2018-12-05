package com.pbtd.manager.vod.phone.mould.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.mould.service.face.IVodPhoneLinkAlbumListInterface;
import com.pbtd.manager.vod.phone.mould.domain.VodPhoneLinkAlbumList;
import com.pbtd.manager.vod.phone.mould.mapper.VodPhoneLinkAlbumListMapper;

@Service
public class VodPhoneLinkAlbumListInterface implements IVodPhoneLinkAlbumListInterface {
	@Autowired
	private VodPhoneLinkAlbumListMapper VodPhoneLinkAlbumListMapper;
	
	
	@Override
	public int delete(Integer id) {
		
		return 0;
	}

	@Override
	public int insert(VodPhoneLinkAlbumList record) {
		
		return 0;
	}

	@Override
	public VodPhoneLinkAlbumList load(Integer id) {
		
		return null;
	}

	@Override
	public int update(VodPhoneLinkAlbumList record) {
		
		return 0;
	}

	@Override
	public List<VodPhoneLinkAlbumList> find(Map<String, Object> queryParams) {
		
		return null;
	}

	@Override
	public List<VodPhoneLinkAlbumList> page(Map<String, Object> queryParams) {
		
		return null;
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return 0;
	}
	
	@Override
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return VodPhoneLinkAlbumListMapper.findalbummaxVSminsequence(map);
	}
	
	@Override
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return VodPhoneLinkAlbumListMapper.findalbumsequence(map);
	}

	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return VodPhoneLinkAlbumListMapper.findalbumsequencesum(queryParams);
	}
	
	@Override
	public int updatealbumsequence(Map<String, Object> map) {
		return VodPhoneLinkAlbumListMapper.updatealbumsequence(map);
	}
}
