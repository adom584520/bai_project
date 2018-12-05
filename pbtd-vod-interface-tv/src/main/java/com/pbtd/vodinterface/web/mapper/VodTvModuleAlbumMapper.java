package com.pbtd.vodinterface.web.mapper;

import java.util.List;
import java.util.Map;

public interface VodTvModuleAlbumMapper {

    List<Map<String,Object>> select(Map<String,Object> queryParams);

}