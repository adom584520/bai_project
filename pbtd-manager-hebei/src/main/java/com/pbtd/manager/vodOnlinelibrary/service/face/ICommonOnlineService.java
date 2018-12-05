package com.pbtd.manager.vodOnlinelibrary.service.face;

import java.util.Map;

import com.pbtd.manager.vod.common.actors.domain.Vodactors;
import com.pbtd.manager.vod.common.corner.domain.VodCollectfeesbag;
import com.pbtd.manager.vod.common.corner.domain.VodCorner;

public interface ICommonOnlineService {
	int  resetactors(Map<String,Object> map);//重置演员
	int  resetcorner(Map<String,Object> map);//重置角标
	int  restcollectfeesbag(Map<String,Object> map);//重置付费包
	int  restmasterplateSon(Map<String,Object> map);//重置模版详情
	int  restSysParam(Map<String,Object> map);//重置模版详情
}
