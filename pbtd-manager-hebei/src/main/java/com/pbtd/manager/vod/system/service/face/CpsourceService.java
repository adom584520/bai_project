package com.pbtd.manager.vod.system.service.face;

import java.util.Map;

import com.pbtd.manager.vod.system.domain.Cpsource;

public interface CpsourceService {
	public int insert(Cpsource cpsource);
	public int count(Map<String,Object> map);
} 
