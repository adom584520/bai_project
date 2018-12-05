package com.pbtd.manager.vod.phone.album.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.album.mapper.VodAlbuminfoStatusMapper;
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfoStatusService;
@Service
public class VodAlbuminfoStatusService implements IVodAlbuminfoStatusService {

	@Autowired
	private VodAlbuminfoStatusMapper vodalbumstatus;
	
	@Override
	public int updatestatus(Map<String, Object> m) {
		vodalbumstatus.updatestatus(m);
		return 0;
	}

	@Override
	public int updatepaid(Map<String, Object> m) {
		vodalbumstatus.updatepaid(m);
		return 0;
	}

}
