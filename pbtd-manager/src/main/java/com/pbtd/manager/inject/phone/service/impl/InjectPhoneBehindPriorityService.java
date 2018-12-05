package com.pbtd.manager.inject.phone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pbtd.manager.inject.phone.service.face.IInjectPhoneBehindPriorityService;
import com.pbtd.manager.inject.phone.mapper.InjectPhoneBehindPriorityMapper;

@Service
public class InjectPhoneBehindPriorityService implements IInjectPhoneBehindPriorityService {

	@Autowired
	private InjectPhoneBehindPriorityMapper injectPhoneBehindPriorityMapper;

	/**
	 * 注入调用方法
	 * 新增专辑，剧集时调用
	 */
	public int dealInjection(){
		try{
			this.injectPhoneBehindPriorityMapper.saveNewAlbum();
			this.injectPhoneBehindPriorityMapper.saveNewVideo();
			this.injectPhoneBehindPriorityMapper.updateNewAlbumPriority();
			this.injectPhoneBehindPriorityMapper.updateNewVideoPriority();
			this.injectPhoneBehindPriorityMapper.updateNewFollowVideoPriority();
			
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
	  int newVideoNum=this.injectPhoneBehindPriorityMapper.findNewVideoNum();
	  return 1;
	}
	

	
}
