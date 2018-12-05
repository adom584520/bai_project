package com.pbtd.playuser.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pbtd.playuser.domain.UserBaseInfo;

public interface UserBaseInfoMapper {
	UserBaseInfo select(HashMap<?, ?> tempBean);

	UserBaseInfo selectbymobile(String mobile);
	
	int insert(HashMap<?, ?> tempBean);
	int count();
	int countindate(Map<String,Object> queryParams);
	
	int selectdateaddcount(Map<String,Object> queryParams);

}