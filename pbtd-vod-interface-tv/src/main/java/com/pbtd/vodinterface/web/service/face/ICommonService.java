package com.pbtd.vodinterface.web.service.face;

import java.util.List;
import java.util.Map;

public interface ICommonService {
	List<Map<String,Object>> getactors(Map<String,Object> queryParams);
	List<Map<String,Object>> getactorlist(Map<String,Object> queryParams);
	List<Map<String,Object>> getChannel(Map<String,Object> queryParams);
	List<Map<String,Object>> getlable(Map<String,Object> queryParams);
	List<Map<String,Object>> getspecial(Map<String,Object> queryParams);
	List<Map<String,Object>> getspecialvideo(Map<String,Object> queryParams);
	List<Map<String,Object>> findChannel(Map<String,Object> queryParams);
	List<Map<String,Object>> getlabeltype(Map<String,Object> queryParams);
}
