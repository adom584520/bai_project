package com.pbtd.manager.vodOnlinelibrary.service.face;

import java.util.Map;


public interface ITvOnlineService {
	int  resetalbum(Map<String,Object> map);//重置专辑
	int  resetchannel(Map<String,Object> map);//重置频道
	int  resetlabel(Map<String,Object> map);//重置标签
	int  resetspecial(Map<String,Object> map);//重置专题
}
