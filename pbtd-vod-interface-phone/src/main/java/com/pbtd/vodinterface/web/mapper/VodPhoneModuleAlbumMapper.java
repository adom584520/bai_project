package com.pbtd.vodinterface.web.mapper;

import java.util.List;
import java.util.Map;

public interface VodPhoneModuleAlbumMapper {

    List<Map<String,Object>> select(Map<String,Object> queryParams);
    List<Map<String,Object>> selectitemdata(Map<String,Object> queryParams);
    List<Map<String,Object>> selectlastitemdata(Map<String,Object> queryParams);
    int count(Map<String,Object> queryParams);

}