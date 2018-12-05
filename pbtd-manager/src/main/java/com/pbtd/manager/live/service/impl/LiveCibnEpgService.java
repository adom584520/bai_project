package com.pbtd.manager.live.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.live.domain.LiveCibnEpg;
import com.pbtd.manager.live.mapper.LiveCibnEpgMapper;
import com.pbtd.manager.live.service.ILiveCibnEpgService;
import com.pbtd.manager.live.spider.LiveCibnEpgInterface;

@Service
public class LiveCibnEpgService implements ILiveCibnEpgService {
	@Autowired
	private LiveCibnEpgMapper liveCibnEpgMapper;

	@Override
	public List<LiveCibnEpg> getcibnepg() {
		String startdate =  LiveCibnEpgInterface.getCurrentDate(3); 
		return liveCibnEpgMapper.getcibnepg(startdate);
	}

	
}
