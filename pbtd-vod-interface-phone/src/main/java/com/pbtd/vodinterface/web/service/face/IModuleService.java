package com.pbtd.vodinterface.web.service.face;

import java.util.List;
import java.util.Map;

public interface IModuleService {
	List<Map<String,Object>> getModuleAlbum(Map<String,Object> queryParams);
	List<Map<String,Object>> getModuleAlbumforOne(Map<String,Object> queryParams);
	
}
