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
	public List<VodTvchannel> page(int start, int limit, Map<String, Object> queryParams) {
	
		return vodchannelMapper.page(start, limit, queryParams);
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
	public int insert(VodTvchannel vodchannel) {
		return vodchannelMapper.insert(vodchannel);
	}

	@Override
	public int update(VodTvchannel vodchannel) {
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
		//获取原始排序序号
		        Map<String,Object> queryParams=new HashMap<String,Object>();
		        queryParams.put("channelCode",map.get("channelCode"));
				Map<String, Object> map1=vodchannelMapper.findalbummaxVSminsequence(queryParams);
				int max=0;
				if(map1!=null){
					max=Integer.parseInt(map1.get("max").toString()==null?"0":map1.get("max").toString());
				}
				map.put("sequence", max+=1);
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
	public int insertjson(VodTvchannel vodchannel) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("parentCode", vodchannel.getParentCode());
		queryParams.put("levels", vodchannel.getLevels());
		//获取排序序号
		Map<String, Object> map=vodchannelMapper.findsequence(queryParams);
		int max=0;
		if(map!=null){
			max=Integer.parseInt(map.get("max").toString()==null?"0":map.get("max").toString());
		}
		vodchannel.setSequence(max+=1);
		return vodchannelMapper.insertjson(vodchannel);
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
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return vodchannelMapper.findalbummaxVSminsequence(map);
	}

	@Override
	public Map<String, Object> findmaxVSminsequence(Map<String, Object> map) {
		return vodchannelMapper.findmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams) {
		return vodchannelMapper.findsequencesum(queryParams);
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
	public int updatesequence(VodTvchannel vodchannel) {
		return vodchannelMapper.updatesequence(vodchannel);
	}

	@Override
	public VodTvchannel loadone() {
		return vodchannelMapper.loadone();
	}

	@Override
	public VodTvchannel loadbychannel(String channel) {
		return vodchannelMapper.loadbychannel(channel);
	}

	
	
	

}
