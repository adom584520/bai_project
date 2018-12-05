package com.pbtd.playuser.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.domain.LiveCibnNextEpg;
import com.pbtd.playuser.domain.UserLiveCollectInfo;
import com.pbtd.playuser.mapper.UserLiveCollectInfoMapper;
import com.pbtd.playuser.page.LiveCollectResult;
import com.pbtd.playuser.service.IUserLiveCollectInfoService;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.JsonServlet;


@Service  
public class UserLiveCollectInfoService implements IUserLiveCollectInfoService{

	@Autowired
	private UserLiveCollectInfoMapper  userLiveCollectMapper;

	@Override
	public JsonMessage livecollect(HashMap<String,Object> params){
		userLiveCollectMapper.delete(params);
		userLiveCollectMapper.insert(params);
		return new JsonMessage(1,"收藏节目成功");
	}

	@Override
	public JsonMessage livecollectdelete(HashMap<String, Object> params) {
		int a = userLiveCollectMapper.delete(params);
		return new JsonMessage(1,"删除"+a+"条收藏记录成功");
	}

	@Override
	public JsonMessage isnotlivecollect(HashMap<String, Object> params) {
		List<UserLiveCollectInfo> livecollect  =  userLiveCollectMapper.select(params);
		if(livecollect == null || livecollect.size() == 0 ){
			return new JsonMessage(0,"该节目未收藏");
		}else{
			return new JsonMessage(1,"已收藏");
		}
	}

	@Override
	public JsonMessage liveCollectList(HashMap<String, Object> params) {
		List<UserLiveCollectInfo> livecollectlist   = userLiveCollectMapper.select(params);
		if(livecollectlist == null || livecollectlist.size() == 0 ){
			return new JsonMessage(0,"数据为空");
		}else{
			return new LiveCollectResult<List<UserLiveCollectInfo>>(livecollectlist);
		}
	}

	@Override
	public JsonMessage liveCollectListEpg(HashMap<String, Object> params) {
		Map<Integer ,LiveCibnNextEpg> map = JsonServlet.getJsonObjectNowEpg();
		List<UserLiveCollectInfo> livecollectlist   = userLiveCollectMapper.select(params);
		List<LiveCibnNextEpg> list = new ArrayList<>();
		if(livecollectlist.size() == 0){
			return new JsonMessage(0,"数据为空");
		}
		for (UserLiveCollectInfo liveCollect : livecollectlist) {
			Integer video = liveCollect.getVideoid();
			if(video == null){
				LiveCibnNextEpg livecibn = new LiveCibnNextEpg();
			//	livecibn.setVideoId(liveCollect.getVideoid());
				livecibn.setChnCode(liveCollect.getChncode());
				livecibn.setTypeName("暂无数据");
				livecibn.setStartTime(0);
				livecibn.setEndTime(0);
				livecibn.setNextStartTime(0);
				livecibn.setNextEndTime(0);
				livecibn.setNextEpgName("暂无下一个节目");
				list.add(livecibn);
				continue;
			}
			LiveCibnNextEpg liveepg = map.get(Integer.valueOf(video));
			if(liveepg == null ){
				//查询自己数据库
				LiveCibnNextEpg livecibn = new LiveCibnNextEpg();
				livecibn.setVideoId(liveCollect.getVideoid());
				livecibn.setChnCode(liveCollect.getChncode());
				livecibn.setTypeName("暂无数据");
				livecibn.setStartTime(0);
				livecibn.setEndTime(0);
				livecibn.setNextStartTime(0);
				livecibn.setNextEndTime(0);
				livecibn.setNextEpgName("暂无下一个节目");
				list.add(livecibn);
			}else{
				liveepg.setChnCode(liveCollect.getChncode());
				list.add(liveepg);
			}
		}
		return new LiveCollectResult<List<LiveCibnNextEpg>>(list);
	}

}  