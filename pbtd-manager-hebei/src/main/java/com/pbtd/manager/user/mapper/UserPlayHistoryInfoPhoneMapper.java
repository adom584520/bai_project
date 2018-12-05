package com.pbtd.manager.user.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.user.domain.UserPlayHistoryInfoPhone;

public interface UserPlayHistoryInfoPhoneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPlayHistoryInfoPhone record);
    
    int mobilecount(Map<String,Object> queryParams);

    int insertSelective(UserPlayHistoryInfoPhone record);

    UserPlayHistoryInfoPhone selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPlayHistoryInfoPhone record);

    int updateByPrimaryKey(UserPlayHistoryInfoPhone record);
    
    Integer playusercount(Map<String,Object> queryParams);
    
    Float playtimecount(Map<String,Object> queryParams);
    
	List<Map<String,Object>> select(Map<String,Object> queryParams);
	
	List<Map<String,Object>> selectmobilecount(Map<String,Object> queryParams);
	List<Map<String,Object>> selectmobileseriescount(Map<String,Object> queryParams);
	
	int selectuserkeepcount(Map<String,Object> queryParams);
	int mobileseriespagecount(Map<String,Object> queryParams);

}