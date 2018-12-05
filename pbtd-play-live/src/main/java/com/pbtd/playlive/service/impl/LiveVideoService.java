package com.pbtd.playlive.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.domain.LiveChannel;
import com.pbtd.playlive.domain.LiveVideo;
import com.pbtd.playlive.domain.LiveVideoEpg;
import com.pbtd.playlive.mapper.LiveChannelMapper;
import com.pbtd.playlive.mapper.LiveVideoMapper;
import com.pbtd.playlive.page.DataResult;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILiveVideoService;

@Service
public class LiveVideoService implements ILiveVideoService{
	@Autowired
	private LiveVideoMapper liveVideoMapper;
	@Autowired
	private LiveChannelMapper liveChannelMapper;

	@Override
	public PageResult<?> querylistallL(Map<String, Object> params) {
		List<LiveVideo> liveVideoList  =  (List<LiveVideo>) params.get("voidelist");
		List<LiveVideoEpg> list  =  new ArrayList<>(); 
		for (LiveVideo liveVideo : liveVideoList) {
			LiveVideoEpg LiveVideoepg = new LiveVideoEpg();
			LiveVideoepg.setVideoid(liveVideo.getVideoid());
			LiveVideoepg.setChncode(liveVideo.getChncode());
			LiveVideoepg.setPackagecode(liveVideo.getPackagecode());
			LiveVideoepg.setTitle(liveVideo.getTitle());	
			LiveVideoepg.setStartTime(liveVideo.getStarttime()/1000);
			LiveVideoepg.setEndTime(liveVideo.getEndtime()/1000);
			{
			LiveChannel livechannel = 	liveChannelMapper.selectoldcode(liveVideo.getChncode());
			LiveVideoepg.setPlayUrl(livechannel.getPlayurl().replace("live.m3u8","history.m3u8"));
			//LiveVideoepg.setPlayUrl2(livechannel.getPlayurl2());
			LiveVideoepg.setChnName(livechannel.getChnname());
			}
			list.add(LiveVideoepg);
		}
		return new PageResult<DataResult<List<LiveVideoEpg>>>(new DataResult<List<LiveVideoEpg>>((int)list.size(), list));
	}

	@Override
	public List<LiveVideo> selectbykey(String packagecode) {                                                                      
		return liveVideoMapper.selectBycode(packagecode);
	}

}
