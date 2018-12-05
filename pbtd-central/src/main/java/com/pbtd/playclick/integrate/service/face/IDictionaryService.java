package com.pbtd.playclick.integrate.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.playclick.integrate.domain.Dictionary;

public interface IDictionaryService {
	/**
	 * 查询数据字典
	 * @param params 查询参数
	 * @param obj 数据库对象名例如T_ZXBZ_ZZMM,则obj为Zzmc
	 * @return
	 */
	List<Dictionary> findDictionary(Map<String,Object> params,String obj);
	List<Map<String,Object>> findDictionaryname(Map<String,Object> params,String obj);
}
