package com.pbtd.manager.inject.tv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.tv.mapper.InjectTvBehindPriorityMapper;
import com.pbtd.manager.inject.tv.service.face.IInjectTvBehindPriorityService;


@Service
public class InjectTvBehindPriorityService implements IInjectTvBehindPriorityService {

	@Autowired
	private InjectTvBehindPriorityMapper injectTvBehindPriorityMapper;

	/**
	 * 注入调用方法
	 * 新增专辑，剧集时调用
	 */
	public int dealInjection(){
		try{
			this.injectTvBehindPriorityMapper.saveNewAlbum();
			this.injectTvBehindPriorityMapper.saveNewVideo();
			this.injectTvBehindPriorityMapper.updateNewAlbumPriority();
			this.injectTvBehindPriorityMapper.updateNewVideoPriority();
			this.injectTvBehindPriorityMapper.updateNewFollowVideoPriority();
			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 发送数据到注入平台
	 * @return
	 */
	public int sendData(){
		return 1;
	}
	
	/**
	 * 查询所有待住入剧集
	 */
	public int findNewVideoNum(){
	  int newVideoNum=this.injectTvBehindPriorityMapper.findNewVideoNum();
	  return 1;
	}
	

	
}
