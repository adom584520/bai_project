package com.pbtd.playlive.mapper;

import java.util.HashMap;
import java.util.List;

import com.pbtd.playlive.domain.LivePackage;

public interface LivePackageMapper {
	LivePackage selectByPrimaryKey(LivePackage record);
	List<LivePackage> queryAllPackage(HashMap<?, ?> tempBean);

	void truncateTable();
	
	int insert(LivePackage record);
	int delete(int packageid);
	
    int deleteTimeOut();

}