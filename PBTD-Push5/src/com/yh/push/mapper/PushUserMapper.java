package com.yh.push.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yh.mybatis.sqlmap.SqlMapper;
import com.yh.push.bean.PushUser;

public interface PushUserMapper extends SqlMapper {

	List<PushUser> selectByPrimaryKey(@Param("pUserId")String pUserId,@Param("tvUserId")String tvUserId);
	
	List<PushUser> select(@Param("pUserId")String pUserId,@Param("tvUserId")String tvUserId);
	
	List<PushUser> selectPhoneToken(@Param("pUserId")String pUserId);
	
	List<PushUser> selectTvToken(@Param("tvUserId")String tvUserId);
	
	List<PushUser> selectDel(@Param("tvUserId")String tvUserId);
	
	int deleteByPrimaryKey(@Param("pUserId")String pUserId,@Param("tvUserId")String tvUserId);
	
	int deleteToken(@Param("tvUserId")String tvUserId);

	int UpdatePhoneToken(@Param("pUserId")String pUserId,@Param("pToken")String pToken,@Param("pSystem")String pSystem);
	
	int UpdateTvToken(@Param("tvUserId")String tvUserId,@Param("tvToken")String tvToken);

    int insert(PushUser record);

    int updateByPrimaryKey(PushUser record);
    
    //int deletePhone(@Param("pUserId")String pUserId);
    
    //int deleteTv(@Param("tvUserId")String tvUserId);
    
    /*int updatePrimary(PushUser record);
	
	int updatePrimaryPhone(PushUser record);*/
}