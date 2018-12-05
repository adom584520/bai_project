package com.pbtd.manager.vod.phone.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.mapper.VodChannelMapper;
import com.pbtd.manager.vod.phone.common.service.face.IVodChannelService;
@Service
public class VodChannelService implements IVodChannelService {

	
	@Autowired
	private VodChannelMapper vodchannelMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
	
		return vodchannelMapper.count(queryParams);
	}

	@Override
	public List<Vodchannel> page(Map<String, Object> queryParams) {
	
		return vodchannelMapper.page(queryParams);
	}

	@Override
	public List<Vodchannel> find(Map<String, Object> queryParams) {
	
		return vodchannelMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
	
		return vodchannelMapper.generatePosition(queryParams);
	}

	@Override
	public Vodchannel load(int id) {
	
		return vodchannelMapper.load(id);
	}

	@Override
	public int insert(Vodchannel vodchannel) {
		return vodchannelMapper.insert(vodchannel);
	}

	@Override
	public int update(Vodchannel vodchannel) {
		return vodchannelMapper.update(vodchannel);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
	 
		return vodchannelMapper.deletes(ids);
	}

	@Override
	public int updatestatus(Map<String, Object> map) {
		return vodchannelMapper.updatestatus(map) ;
	}

	@Override
	public int insertalbum(Map<String, Object> map) {
		return vodchannelMapper.insertalbum(map);
	}

	@Override
	public int updatealbumsequence(Map<String, Object> map) {
		return vodchannelMapper.updatealbumsequence(map);
	}

	@Override
	public int deletesalbum(Map<String, Object> map) {
		return vodchannelMapper.deletesalbum(map);
	}

	@Override
	public int countalbum(Map<String, Object> queryParams) {
		return vodchannelMapper.countalbum(queryParams);
	}

	@Override
	public List<Map<String, Object>> pagealbum(Map<String, Object> queryParams) {
		return vodchannelMapper.pagealbum(queryParams);
	}

	@Override
	public List<Map<String, Object>> channelalbum(Map<String, Object> queryParams) {
		return vodchannelMapper.channelalbum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return vodchannelMapper.findalbumsequence(map);
	}

	@Override
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return vodchannelMapper.findalbummaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return vodchannelMapper.findalbumsequencesum(queryParams);
	}

	@Override
	public Map<String, Object> findsequence(Map<String, Object> queryParams) {
		return vodchannelMapper.findsequence(queryParams);
	}

	@Override
	public Map<String, Object> findchannelmaxVSminsequence(Map<String, Object> map) {
		return vodchannelMapper.findchannelmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findchannelsequencesum(Map<String, Object> queryParams) {
		return vodchannelMapper.findchannelsequencesum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findchannelsequence(Map<String, Object> map) {
		return vodchannelMapper.findchannelsequence(map);
	}
	@Override
	public int updateimg(Map<String, Object> Map) {
		return vodchannelMapper.updateimg(Map);
	}

	@Override
	public int updatebindinglabel(Map<String, Object> map) {
		return vodchannelMapper.updatebindinglabel(map);
	}

	@Override
	public Map<String, Object> findchannellabelmaxVSminsequence(Map<String, Object> map) {
		return vodchannelMapper.findchannellabelmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findchannellabelsequencesum(Map<String, Object> queryParams) {
		return vodchannelMapper.findchannellabelsequencesum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findchannellabelsequence(Map<String, Object> map) {
		return vodchannelMapper.findchannellabelsequence(map);
	}

	@Override
	public List<Map<String, Object>> pagelabelforchannel(Map<String, Object> map) {
		return vodchannelMapper.pagelabelforchannel(map);
	}

	@Override
	public int countlabelforchannel(Map<String, Object> map) {
		return vodchannelMapper.countlabelforchannel(map);
	}

	@Override
	public int addbindinglabel(Map<String, Object> map) {
		return vodchannelMapper.addbindinglabel(map);
	}

	@Override
	public int deletebindinglabel(Map<String, Object> map) {
		return vodchannelMapper.deletebindinglabel(map);
	}

	@Override
	public int updatesequence(Vodchannel vodchannel) {
		return vodchannelMapper.updatesequence(vodchannel);
	}
	

}
