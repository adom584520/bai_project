package com.pbtd.manager.vod.tv.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
import com.pbtd.manager.vod.tv.common.mapper.VodTvChannelMapper;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvChannelService;
@Service
public class VodTvChannelService implements IVodTvChannelService {

	
	@Autowired
	private VodTvChannelMapper vodchannelMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
	
		return vodchannelMapper.count(queryParams);
	}

	@Override
	public List<VodTvchannel> page(Map<String, Object> queryParams) {
	
		return vodchannelMapper.page(queryParams);
	}

	@Override
	public List<VodTvchannel> find(Map<String, Object> queryParams) {
	
		return vodchannelMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
	
		return vodchannelMapper.generatePosition(queryParams);
	}

	@Override
	public VodTvchannel load(int id) {
	
		return vodchannelMapper.load(id);
	}
	
	@Override
	public VodTvchannel loadone() {
		return vodchannelMapper.loadone();
	}
	

	@Override
	public int insert(VodTvchannel vodchannel) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("parentCode", vodchannel.getParentCode());
		queryParams.put("levels", vodchannel.getLevels());
		//获取排序序号
		int newsequence=vodchannel.getSequence();
			queryParams.put("maxnum",newsequence);
		vodchannelMapper.addsequce( queryParams);
		return vodchannelMapper.insert(vodchannel);
	}

	@Override
	public int update(VodTvchannel vodchannel) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("parentCode", vodchannel.getParentCode());
		queryParams.put("id", vodchannel.getId());
		queryParams.put("levels", vodchannel.getLevels());
		//获取原始排序序号
		VodTvchannel map1=vodchannelMapper.load(vodchannel.getId());
		int currsequence=map1.getSequence();
		int newsequence=vodchannel.getSequence();
		//比对序号是变大还是变小
		if(currsequence>newsequence){
			queryParams.put("plus", "1");
			queryParams.put("minnum", newsequence);
			queryParams.put("maxnum",currsequence);
		}else{
			queryParams.put("minnum", currsequence);
			queryParams.put("maxnum",newsequence);
		}
		vodchannelMapper.updatesequce( queryParams);
		return vodchannelMapper.update(vodchannel);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
	
		return vodchannelMapper.deletes(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return vodchannelMapper.updateStatus(map);
	}

	@Override
	public int countalbum(Map<String, Object> map) {
		return vodchannelMapper.countalbum(map);
	}

	@Override
	public List<Map<String,Object>>  pagealbum(Map<String, Object> map) {
		return vodchannelMapper.pagealbum(map);
	}

	@Override
	public int saveChannelAlbum(Map<String, Object> map) {
		return vodchannelMapper.saveChannelAlbum(map);
		
	}

	@Override
	public int delChannelAlbum(Map<String, Object> map) {
		return vodchannelMapper.delChannelAlbum(map);
	}

	@Override
	public int updateChannelAlbumSeq(Map<String, Object> map) {
		
		return vodchannelMapper.updateChannelAlbumSeq(map);
	}

	@Override
	public List<Map<String, Object>> channelalbum(Map<String, Object> queryParams) {
		return vodchannelMapper.channelalbum(queryParams);
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
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return vodchannelMapper.findalbumsequence(map);
	}

	@Override
	public Map<String, Object> findsequence(Map<String, Object> map) {
		return vodchannelMapper.findsequence(map);
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
	public int updatesequence(VodTvchannel vodchannel) {
		return vodchannelMapper.updatesequence(vodchannel);
	}
	
	@Override
	public VodTvchannel loadbychannel(String channel) {
		return vodchannelMapper.loadbychannel(channel);
	}
	
	

}
