package com.pbtd.manager.vod.phone.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.mapper.VodChannelMapper;
import com.pbtd.manager.vod.phone.common.service.face.IVodChannelService;
import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
@Service
public class VodChannelService implements IVodChannelService {

	
	@Autowired
	private VodChannelMapper vodchannelMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
	
		return vodchannelMapper.count(queryParams);
	}

	@Override
	public List<Vodchannel> page(int start, int limit, Map<String, Object> queryParams) {
	
		return vodchannelMapper.page(start, limit, queryParams);
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
		//获取排序序号
		        Map<String,Object> queryParam=new HashMap<>();
		        queryParam.put("channelCode", map.get("channelCode"));
				Map<String, Object> map1=vodchannelMapper.findalbummaxVSminsequence(queryParam);
				int max=0;
				if(map1!=null){
					max=Integer.parseInt(map1.get("max").toString()==null?"0":map1.get("max").toString());
				}
				map.put("sequence", max+=1);
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
	public int insertjson(Vodchannel vodchannel) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("levels", vodchannel.getLevels());
		//获取排序序号
		int levels=vodchannel.getLevels();
		 queryParams.put("parentCode", vodchannel.getParentCode());
		Map<String, Object> map=vodchannelMapper.findsequence(queryParams);
		int max=0;
		if(map!=null){
			max=Integer.parseInt(map.get("max").toString()==null?"0":map.get("max").toString());
		}
		vodchannel.setSequence(max+=1);
		return vodchannelMapper.insertjson(vodchannel);
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
	public int updateimg(Map<String, Object> Map) {
		return vodchannelMapper.updateimg(Map);
	}

	@Override
	public int updatesequence(Vodchannel vodchannel) {
		return vodchannelMapper.updatesequence(vodchannel);
	}

	
	@Override
	public Vodchannel loadone() {
		return vodchannelMapper.loadone();
	}

	@Override
	public Vodchannel loadbychannel(String channel) {
		return vodchannelMapper.loadbychannel(channel);
	}

}
