package com.pbtd.playclick.integrate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.integrate.domain.VodMapping;
import com.pbtd.playclick.integrate.mapper.VodMappingMapper;
import com.pbtd.playclick.integrate.service.face.IVodMappingService;
@Service
public class VodMappingService implements IVodMappingService {

	   @Autowired
	 private VodMappingMapper vodmappingmapper;

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodmappingmapper.count(queryParams);
	}

	@Override
	public List<VodMapping> find(Map<String, Object> queryParams) {
		
		return vodmappingmapper.find(queryParams);
	}

	@Override
	public VodMapping load(int id) {
		
		return vodmappingmapper.load(id);
	}

	@Override
	public int insert(VodMapping vodmapping) {
		
		return vodmappingmapper.insert(vodmapping);
	}

	@Override
	public int update(VodMapping vodmapping) {
		
		return vodmappingmapper.update(vodmapping);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return vodmappingmapper.deletes(ids);
	}	
	 

}
