package com.pbtd.vodinterface.web.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.vodinterface.web.domain.VodPhoneModule;

public interface VodPhoneModuleMapper {

    VodPhoneModule load(Map<String, Object> queryParams);

    List<VodPhoneModule> select (Map<String,Object> queryParams);

    List< Map<String,Object>> selectForOne(Map<String,Object> queryParams);
    

}