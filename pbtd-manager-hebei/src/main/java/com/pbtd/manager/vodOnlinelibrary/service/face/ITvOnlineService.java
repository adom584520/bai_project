package com.pbtd.manager.vodOnlinelibrary.service.face;

import java.util.Map;

import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;


public interface ITvOnlineService {
	int  resetalbum(Map<String,Object> map);//重置专辑
	int  resetchannel(Map<String,Object> map);//重置频道
	int  resetlabel(Map<String,Object> map);//重置标签
	int  resetspecial(Map<String,Object> map);//重置专题
	int  resetmodule(Map<String,Object> map);//重置tv模块
	int resetAllmodule(Map<String, Object> map); //重置全部tv频道下模块
	int  resetSingleMoudle(Map<String,Object> map) throws Exception; //重置tv单个模块
	int  resetChannelSeq(VodTvchannel vodTvchannel) throws Exception;//更新单个频道排序
	int resetModuleSeq(Map<String,Object> map) throws Exception;//更新单个模块排序
	
}
