package com.pbtd.manager.vod.page.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.page.domain.Dictionary;


public interface IDictionaryService {
	/**
	 * 查询数据字典
	 * @param params 查询参数
	 * @param obj 数据库对象名例如T_ZXBZ_ZZMM,则obj为Zzmc
	 * @return
	 */
	List<Dictionary> findDictionary(Map<String,Object> params,String obj);
	List<Map<String,Object>> findDictionaryMap(Map<String,Object> params,String obj);
	

	//查询爬取偏移值
	Map<String,Object> getoffset(Map<String,Object> params);
	int updateoffset(Map<String,Object> params);
}
