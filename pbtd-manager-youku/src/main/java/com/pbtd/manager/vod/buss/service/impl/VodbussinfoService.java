package com.pbtd.manager.vod.buss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.buss.domain.Vodbusschannel;
import com.pbtd.manager.vod.buss.domain.Vodbussinfo;
import com.pbtd.manager.vod.buss.mapper.VodbusschannelMapper;
import com.pbtd.manager.vod.buss.mapper.VodbussinfoMapper;
import com.pbtd.manager.vod.buss.service.face.IVodbussinfoService;
@Service
public class VodbussinfoService implements IVodbussinfoService {

	@Autowired
	private VodbussinfoMapper vodbussinfoMapper;
	
	@Autowired
	private VodbusschannelMapper vodbusschannelMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodbussinfoMapper.count(queryParams);
	}

	@Override
	public List<Vodbussinfo> page(int start, int limit, Map<String, Object> queryParams) {
		
		return vodbussinfoMapper.page(start, limit, queryParams);
	}

	@Override
	public List<Vodbussinfo> find(Map<String, Object> queryParams) {
		
		return vodbussinfoMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodbussinfoMapper.generatePosition(queryParams);
	}

	@Override
	public Vodbussinfo load(int id) {
		
		return vodbussinfoMapper.load(id);
	}

	@Override
	public int insert(Vodbussinfo vodbussinfo,Map<String,Object> map) {
		map.put("bussId", vodbussinfo.getBussId());
		vodbusschannelMapper.deletes(map);//删除关联频道
		int maxbussid=vodbusschannelMapper.maxbussId(map);
		Vodbusschannel vodbusschannel=new Vodbusschannel();//初始化管理频道
		vodbusschannel.setBussId(maxbussid);
		vodbusschannel.setGroupId(vodbussinfo.getGroupId());
		String channel=map.get("channelCode")==null?"":map.get("channelCode").toString();
		if(!channel.isEmpty()){
			String[] list=channel.split(",");
			for (int i=1;i<list.length;i++) {
				vodbusschannel.setChannelCode(Integer.parseInt(list[i]));
				vodbusschannelMapper.insert(vodbusschannel);
			}
		}
		return vodbussinfoMapper.insert(vodbussinfo);
	}

	@Override
	public int update(Vodbussinfo vodbussinfo,Map<String,Object> map) {
		map.put("bussId", vodbussinfo.getBussId());
		vodbusschannelMapper.deletes(map);//删除关联频道
		Vodbusschannel vodbusschannel=new Vodbusschannel();//初始化管理频道
		vodbusschannel.setBussId(vodbussinfo.getBussId());
		vodbusschannel.setGroupId(vodbussinfo.getGroupId());
		String channel=map.get("channelCode")==null?"":map.get("channelCode").toString();
		if(!channel.isEmpty()){
			String[] list=channel.split(",");
			for (int i=1;i<list.length;i++) {
				vodbusschannel.setChannelCode(Integer.parseInt(list[i]));
				vodbusschannelMapper.insert(vodbusschannel);
			}
		}
		return vodbussinfoMapper.update(vodbussinfo);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		vodbusschannelMapper.deletes(ids);//删除关联频道		
		return vodbussinfoMapper.deletes(ids);
		
	}

}
