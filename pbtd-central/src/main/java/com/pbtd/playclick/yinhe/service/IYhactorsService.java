package com.pbtd.playclick.yinhe.service;

import com.pbtd.playclick.yinhe.domain.YhActors;

public interface IYhactorsService {
	 	int insert(YhActors ators);
	 	YhActors select(String code,String type);
	    int update(YhActors ators);
	    int deletes(int ids);
	    void addActors(String actors);
}
