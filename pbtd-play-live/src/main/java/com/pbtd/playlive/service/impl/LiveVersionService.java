package com.pbtd.playlive.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.domain.LiveVersion;
import com.pbtd.playlive.domain.LiveVersionEPG;
import com.pbtd.playlive.mapper.LiveVersionMapper;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILiveVersionService;

@Service
public class LiveVersionService implements ILiveVersionService{
	@Autowired
	private LiveVersionMapper liveVersionMapper;

	



	@Override
	public PageResult<?> querylistallL(Map<String, Object> params) {
		LiveVersionEPG  liveVersionEPG = new LiveVersionEPG();
		LiveVersion liveVersion = liveVersionMapper.selectByPrimaryKey(null);
		if(liveVersion != null){
			liveVersionEPG.setIsNumChange(liveVersion.getIsnumchanage());
			liveVersionEPG.setIsPlayContinue(liveVersion.getIsplaycontinue());
			liveVersionEPG.setShowType(liveVersion.getShowtype());
			liveVersionEPG.setIsShowLiveList(liveVersion.getIsshowlivelist());
			liveVersionEPG.setLiveNavStyle(liveVersion.getIssupportseqnum());
			liveVersionEPG.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			return new PageResult<LiveVersionEPG>(liveVersionEPG);
		}else{
			return new PageResult<Object>(0,"版本未上线");
		}
	}

}
