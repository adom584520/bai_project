package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.CpInfo;
import com.pbtd.manager.query.CpInfoQueryObject;
import com.pbtd.manager.util.PageResult;

public interface CpInfoService {

	PageResult queryList(CpInfoQueryObject qo);

	void insert(CpInfo cpInfo);

	void update(CpInfo cpInfo);

	void delete(Integer id);
	
	List<CpInfo> queryAll();
	
	CpInfo queryByCpCode(String cpCode);
}
