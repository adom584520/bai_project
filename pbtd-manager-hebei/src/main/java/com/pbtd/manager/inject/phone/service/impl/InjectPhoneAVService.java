package com.pbtd.manager.inject.phone.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.phone.mapper.InjectPhoneAlbumMapper;
import com.pbtd.manager.inject.phone.mapper.InjectPhoneAlbumVideoMapper;
import com.pbtd.manager.inject.phone.mapper.InjectPhonePriorityMapper;
import com.pbtd.manager.inject.phone.service.face.IInjectPhoneAVService;
import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.album.service.impl.VodAlbuminfoService;

@Service
public class InjectPhoneAVService implements IInjectPhoneAVService {

	@Autowired
	private InjectPhoneAlbumMapper injectPhoneAlbumMapper;
	
	@Autowired
	private VodAlbuminfoService vodAlbuminfoService;
	
	@Autowired
	private InjectPhoneAlbumVideoMapper injectPhoneAlbumVideoMapper;
	
	@Autowired
	private InjectPhonePriorityMapper injectPhonePriorityMapper;
	
	
	/**
	 * 保存专辑 到注入关联表中
	 * @throws Exception 
	 */
	@Override
	public void saveNewAlbums (Map<String, Object> map) throws Exception   {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("seriesCode", map.get("seriesCode"));
		
		try{
			List<Vodalbuminfo> listAlbum = vodAlbuminfoService.find(params);
			List<Map<String,Object>> listInjectAlbum=injectPhoneAlbumMapper.findInjectAlbum(params);
			//vod有记录
			if(listAlbum!=null && listAlbum.size()>0){
				Vodalbuminfo vodAlbumInfo=listAlbum.get(0);
				Map<String,Object> channelParams=new HashMap<String,Object>();
				channelParams.put("channelCode", vodAlbumInfo.getChannel());
				//频道对应优先级
				List<Map<String,Object>> listChannelPriority=injectPhonePriorityMapper.findChannelPriority(channelParams);
				//inject无记录
				if(listInjectAlbum==null || listInjectAlbum.size()==0){
					if(listChannelPriority!=null && listChannelPriority.size()>0){
						Map<String,Object> channelPriority=listChannelPriority.get(0);
						//优先使用频道优先级
						String priority=(channelPriority.get("priority")==null?1:channelPriority.get("priority")).toString();
						params.put("zxPriority", priority);
						params.put("hwPriority", priority);
					}
					injectPhoneAlbumMapper.saveInjectAlbum(params);
				}
			}
		}catch(Exception e){
			throw new Exception("saveNewAlbums时：",e);
		}
	}

	/**
	 * 保存剧集 到注入关联表中
	 * @throws Exception 
	 */
	@Override
	public void saveNewAlbumVideo(Map<String, Object> map) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("seriesCode", map.get("seriesCode"));
		params.put("drama",map.get("drama"));
		
		try{
			List<Map<String,Object>> listAlbumVideo = vodAlbuminfoService.findAlbumsinfovideo(params);
			List<Map<String,Object>> listInjectVido = injectPhoneAlbumVideoMapper.findAlbumVideoBySeriesAndDrama(params);
			//vod中存在剧集
			if(listAlbumVideo!=null && listAlbumVideo.size()>0){
				Map<String,Object> albumVideo=listAlbumVideo.get(0);
				//inject中不存在
				if(albumVideo==null || albumVideo.size()==0){
					//inject-专辑
					List<Map<String,Object>> listInjectAlbum=injectPhoneAlbumVideoMapper.findAlbumVideoBySeriesAndDrama(params);
					
					if(listInjectAlbum!=null && listInjectAlbum.size()>0){
						Map<String,Object> injectAlbum=listInjectAlbum.get(0);
						String zxPriority=(injectAlbum.get("zxPriority")==null?1:injectAlbum.get("zxPriority")).toString();
						String hwPriority=(injectAlbum.get("hwPriority")==null?1:injectAlbum.get("hwPriority")).toString();
						params.put("zxPriority", zxPriority);
						params.put("hwPriority", hwPriority);
					}
					injectPhoneAlbumVideoMapper.saveAlbumVideo(params);
				}
				
			}
		}catch(Exception e){
			throw new Exception("saveNewAlbumVideo时：",e);
		}
	}

}
