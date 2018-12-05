package com.pbtd.manager.inject.page.service.face;

import java.util.List;
import java.util.Map;

public interface  InjectTvOutPutService {
	 int countHwAlbumsinfovideo(Map<String, Object> queryParams);
	 List<Map<String,Object>> findHwAlbumsinfovideo(Map<String, Object> queryParams);
	 int countZxAlbumsinfovideo(Map<String, Object> queryParams);
	 List<Map<String,Object>> findZxAlbumsinfovideo(Map<String, Object> queryParams);
	 void updateTvInject(Map<String, Object> queryParams); 
}
